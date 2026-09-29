package com.agacostays.room.storage.impl;

import com.agacostays.room.exception.StorageException;
import com.agacostays.room.storage.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Primary
public class LocalStorageServiceImpl implements StorageService {

    private final Path root;

    public LocalStorageServiceImpl(@Value("${storage.local.root:uploads}") String root) {
        this.root = Path.of(root).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.root);
        } catch (IOException e) {
            throw new StorageException("Could not create storage directory: " + e.getMessage());
        }
    }

    @Override
    public String storeRoomPhoto(Long branchId, Long roomId, MultipartFile file) {
        try {
            String original = StringUtils.cleanPath(file.getOriginalFilename() == null ? "photo" : file.getOriginalFilename());
            String extension = "";
            int dot = original.lastIndexOf('.');
            if (dot >= 0) extension = original.substring(dot);
            Path dir = root.resolve("rooms").resolve(String.valueOf(branchId)).resolve(String.valueOf(roomId));
            Files.createDirectories(dir);
            String name = UUID.randomUUID() + extension;
            Path target = dir.resolve(name).normalize();
            if (!target.startsWith(dir)) throw new StorageException("Invalid file path");
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/rooms/" + branchId + "/" + roomId + "/" + name;
        } catch (IOException e) {
            throw new StorageException("Could not store photo: " + e.getMessage());
        }
    }

    @Override
    public void delete(String storedPath) {
        if (storedPath == null || storedPath.isBlank()) return;
        String relative = storedPath.replaceFirst("^/uploads/", "");
        try {
            Files.deleteIfExists(root.resolve(relative).normalize());
        } catch (IOException e) {
            throw new StorageException("Could not delete photo: " + e.getMessage());
        }
    }
}
