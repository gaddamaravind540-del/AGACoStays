package com.agacostays.branch.storage.impl;
import com.agacostays.branch.exception.StorageException;
import com.agacostays.branch.storage.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.*;
import java.util.UUID;
@Service
public class LocalStorageServiceImpl implements StorageService {
 private final Path root;
 private final String publicBaseUrl;
 public LocalStorageServiceImpl(@Value("${app.storage.local-directory:uploads}")String dir,@Value("${app.storage.public-base-url:http://localhost:8083/files}")String base){
  root=Paths.get(dir).toAbsolutePath().normalize();publicBaseUrl=base;
  try{Files.createDirectories(root);}catch(IOException e){throw new StorageException("Could not initialize storage");}
 }
 public String store(String folder,MultipartFile file){
  if(file==null||file.isEmpty())throw new StorageException("File is empty");
  String original=StringUtils.cleanPath(file.getOriginalFilename()==null?"file":file.getOriginalFilename());
  String ext=original.contains(".")?original.substring(original.lastIndexOf(".")):"";
  String name=UUID.randomUUID()+ext.toLowerCase();
  Path target=root.resolve(folder).resolve(name).normalize();
  if(!target.startsWith(root))throw new StorageException("Invalid storage path");
  try{
   Files.createDirectories(target.getParent());file.transferTo(target);
   return publicBaseUrl+"/"+folder+"/"+name;
  }catch(IOException e){throw new StorageException("Unable to store file");}
 }
 public void delete(String url){/* Safe local storage cleanup can be added when deployment path is fixed. */}
}
