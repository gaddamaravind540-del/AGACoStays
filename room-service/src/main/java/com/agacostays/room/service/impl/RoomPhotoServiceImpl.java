package com.agacostays.room.service.impl;

import com.agacostays.room.dto.response.RoomPhotoResponse;
import com.agacostays.room.entity.RoomPhoto;
import com.agacostays.room.exception.RoomNotFoundException;
import com.agacostays.room.exception.RoomPhotoNotFoundException;
import com.agacostays.room.mapper.RoomPhotoMapper;
import com.agacostays.room.repository.RoomPhotoRepository;
import com.agacostays.room.repository.RoomRepository;
import com.agacostays.room.security.BranchAccessValidator;
import com.agacostays.room.security.CurrentUserProvider;
import com.agacostays.room.storage.StorageService;
import com.agacostays.room.util.FileUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoomPhotoServiceImpl implements com.agacostays.room.service.RoomPhotoService {

    private final RoomPhotoRepository photoRepository;
    private final RoomRepository roomRepository;
    private final RoomPhotoMapper mapper;
    private final StorageService storageService;
    private final BranchAccessValidator accessValidator;
    private final CurrentUserProvider currentUserProvider;

    public RoomPhotoServiceImpl(RoomPhotoRepository photoRepository, RoomRepository roomRepository, RoomPhotoMapper mapper,
                                StorageService storageService, BranchAccessValidator accessValidator,
                                CurrentUserProvider currentUserProvider) {
        this.photoRepository = photoRepository;
        this.roomRepository = roomRepository;
        this.mapper = mapper;
        this.storageService = storageService;
        this.accessValidator = accessValidator;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomPhotoResponse> list(Long branchId, Long roomId) {
        ensureRoom(branchId, roomId);
        return photoRepository.findByBranchIdAndRoomIdOrderByPrimaryPhotoDescCreatedAtAsc(branchId, roomId)
                .stream().map(mapper::toResponse).toList();
    }

    @Override
    public List<RoomPhotoResponse> upload(Long branchId, Long roomId, List<MultipartFile> files, String caption, boolean primary) {
        accessValidator.validate(branchId);
        ensureRoom(branchId, roomId);
        if (files == null || files.isEmpty()) throw new IllegalArgumentException("At least one photo is required");
        List<RoomPhoto> saved = new ArrayList<>();
        boolean makePrimary = primary;
        for (MultipartFile file : files) {
            FileUploadUtil.validateImage(file);
            String url = storageService.storeRoomPhoto(branchId, roomId, file);
            if (makePrimary) clearPrimary(branchId, roomId);
            RoomPhoto photo = RoomPhoto.builder().branchId(branchId).roomId(roomId).photoUrl(url)
                    .caption(caption).primaryPhoto(makePrimary).uploadedBy(currentUserProvider.getCurrentUser().userId()).build();
            saved.add(photoRepository.save(photo));
            makePrimary = false;
        }
        return saved.stream().map(mapper::toResponse).toList();
    }

    @Override
    public void delete(Long branchId, Long roomId, Long photoId) {
        accessValidator.validate(branchId);
        RoomPhoto photo = findPhoto(branchId, roomId, photoId);
        storageService.delete(photo.getPhotoUrl());
        photoRepository.delete(photo);
    }

    @Override
    public RoomPhotoResponse updateCaption(Long branchId, Long roomId, Long photoId, String caption) {
        accessValidator.validate(branchId);
        RoomPhoto photo = findPhoto(branchId, roomId, photoId);
        photo.setCaption(caption);
        return mapper.toResponse(photoRepository.save(photo));
    }

    @Override
    public RoomPhotoResponse replace(Long branchId, Long roomId, Long photoId, MultipartFile file, String caption) {
        accessValidator.validate(branchId);
        FileUploadUtil.validateImage(file);
        RoomPhoto photo = findPhoto(branchId, roomId, photoId);
        String oldPath = photo.getPhotoUrl();
        String newPath = storageService.storeRoomPhoto(branchId, roomId, file);
        photo.setPhotoUrl(newPath);
        if (caption != null) photo.setCaption(caption);
        RoomPhoto saved = photoRepository.save(photo);
        storageService.delete(oldPath);
        return mapper.toResponse(saved);
    }

    @Override
    public RoomPhotoResponse setPrimary(Long branchId, Long roomId, Long photoId) {
        accessValidator.validate(branchId);
        RoomPhoto photo = findPhoto(branchId, roomId, photoId);
        clearPrimary(branchId, roomId);
        photo.setPrimaryPhoto(true);
        return mapper.toResponse(photoRepository.save(photo));
    }

    private RoomPhoto findPhoto(Long branchId, Long roomId, Long photoId) {
        return photoRepository.findByPhotoIdAndBranchIdAndRoomId(photoId, branchId, roomId)
                .orElseThrow(() -> new RoomPhotoNotFoundException("Photo " + photoId + " not found"));
    }

    private void ensureRoom(Long branchId, Long roomId) {
        if (roomRepository.findByRoomIdAndBranchId(roomId, branchId).isEmpty()) {
            throw new RoomNotFoundException("Room " + roomId + " not found in branch " + branchId);
        }
    }

    private void clearPrimary(Long branchId, Long roomId) {
        photoRepository.findByBranchIdAndRoomIdOrderByPrimaryPhotoDescCreatedAtAsc(branchId, roomId)
                .forEach(p -> { if (p.isPrimaryPhoto()) p.setPrimaryPhoto(false); });
        photoRepository.flush();
    }
}
