package com.agacostays.room.storage.impl;

import com.agacostays.room.storage.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/** Adapter placeholder for future S3/GCS/Azure integration. */
@Service("cloudStorageService")
public class CloudStorageServiceImpl implements StorageService {
    @Override
    public String storeRoomPhoto(Long branchId, Long roomId, MultipartFile file) {
        throw new UnsupportedOperationException("Cloud storage is not configured yet");
    }

    @Override
    public void delete(String storedPath) {
        throw new UnsupportedOperationException("Cloud storage is not configured yet");
    }
}
