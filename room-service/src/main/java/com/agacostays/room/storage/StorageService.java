package com.agacostays.room.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String storeRoomPhoto(Long branchId, Long roomId, MultipartFile file);
    void delete(String storedPath);
}
