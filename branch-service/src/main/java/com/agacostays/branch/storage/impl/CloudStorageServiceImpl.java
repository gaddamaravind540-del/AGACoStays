package com.agacostays.branch.storage.impl;
import com.agacostays.branch.storage.StorageService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
@Profile("cloud")
public class CloudStorageServiceImpl implements StorageService {
 public String store(String folder,MultipartFile file){
  throw new UnsupportedOperationException("Configure cloud storage provider before using cloud profile");
 }
 public void delete(String url){}
}
