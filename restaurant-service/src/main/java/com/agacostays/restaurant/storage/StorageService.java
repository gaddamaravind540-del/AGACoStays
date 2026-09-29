package com.agacostays.restaurant.storage;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
public interface StorageService {
    String store(MultipartFile file, String folder) throws IOException;
    void delete(String storedPath) throws IOException;
}
