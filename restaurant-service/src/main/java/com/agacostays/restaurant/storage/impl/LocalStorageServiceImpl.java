package com.agacostays.restaurant.storage.impl;

import com.agacostays.restaurant.storage.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class LocalStorageServiceImpl implements StorageService {
    private final Path root;
    public LocalStorageServiceImpl(@Value("${storage.local.root:uploads}") String root) {
        this.root = Paths.get(root).toAbsolutePath().normalize();
    }
    @Override public String store(MultipartFile file, String folder) throws IOException {
        Files.createDirectories(root.resolve(folder));
        String safe = System.currentTimeMillis() + "-" + Path.of(file.getOriginalFilename()).getFileName();
        Path target = root.resolve(folder).resolve(safe);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/" + folder + "/" + safe;
    }
    @Override public void delete(String storedPath) throws IOException {
        if (storedPath == null) return;
        String relative = storedPath.replaceFirst("^/uploads/?","");
        Files.deleteIfExists(root.resolve(relative).normalize());
    }
}
