package com.agacostays.branch.security;
import com.agacostays.branch.exception.FileUploadException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Component public class FileUploadSecurityValidator {
 public void validate(MultipartFile f){
  if(f==null||f.isEmpty())throw new FileUploadException("Photo file is required");
  String type=f.getContentType();
  if(type==null||!(type.equals("image/jpeg")||type.equals("image/png")||type.equals("image/webp"))){
   throw new FileUploadException("Only JPEG, PNG or WEBP images are supported");
  }
  if(f.getSize()>10_000_000)throw new FileUploadException("Photo size must be 10 MB or less");
 }
}
