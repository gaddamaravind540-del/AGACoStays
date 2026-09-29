package com.agacostays.room.security;

import org.springframework.web.multipart.MultipartFile;

public final class FileUploadSecurityValidator {
    private FileUploadSecurityValidator() {}

    public static void validate(MultipartFile file, long maxBytes) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }
        if (file.getSize() > maxBytes) {
            throw new IllegalArgumentException("Uploaded file is too large");
        }
    }
}
