package com.agacostays.branch.config;
import org.springframework.beans.factory.annotation.Value;import org.springframework.context.annotation.Configuration;import org.springframework.web.servlet.config.annotation.*;
@Configuration public class FileResourceConfig implements WebMvcConfigurer{
 private final String dir;
 public FileResourceConfig(@Value("${app.storage.local-directory:uploads}")String dir){this.dir=java.nio.file.Paths.get(dir).toAbsolutePath().normalize().toString();}
 @Override public void addResourceHandlers(ResourceHandlerRegistry registry){
  registry.addResourceHandler("/files/**").addResourceLocations("file:"+dir+"/");
 }
}
