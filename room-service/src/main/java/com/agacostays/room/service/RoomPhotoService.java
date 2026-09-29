package com.agacostays.room.service;

import com.agacostays.room.dto.response.RoomPhotoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RoomPhotoService {
    List<RoomPhotoResponse> list(Long branchId, Long roomId);
    List<RoomPhotoResponse> upload(Long branchId, Long roomId, List<MultipartFile> files, String caption, boolean primary);
    void delete(Long branchId, Long roomId, Long photoId);
    RoomPhotoResponse updateCaption(Long branchId, Long roomId, Long photoId, String caption);
    RoomPhotoResponse replace(Long branchId, Long roomId, Long photoId, MultipartFile file, String caption);
    RoomPhotoResponse setPrimary(Long branchId, Long roomId, Long photoId);
}
