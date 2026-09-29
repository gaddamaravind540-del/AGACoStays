package com.agacostays.branch.storage;
import org.springframework.web.multipart.MultipartFile;
public interface StorageService {
 String store(String folder,MultipartFile file);
 void delete(String url);
}
