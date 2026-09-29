package com.agacostays.room.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public final class FileUploadUtil {
    private static final Set<String> ALLOWED = Set.of("image/jpeg", "image/png", "image/webp");

    private FileUploadUtil() {}

    public static void validateImage(MultipartFile file) {
        if (file == null || file.isEmpty()) throw new IllegalArgumentException("Image is required");
        if (!ALLOWED.contains(file.getContentType())) throw new IllegalArgumentException("Only JPEG, PNG or WEBP images are allowed");
        if (file.getSize() > 5 * 1024 * 1024) throw new IllegalArgumentException("Image must be <= 5 MB");
    }
}
