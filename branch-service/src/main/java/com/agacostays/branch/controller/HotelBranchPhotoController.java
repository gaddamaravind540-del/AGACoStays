package com.agacostays.branch.controller;
import com.agacostays.branch.dto.request.HotelBranchPhotoRequest;import com.agacostays.branch.dto.response.*;import com.agacostays.branch.enums.BranchPhotoType;import com.agacostays.branch.security.CurrentUserProvider;import com.agacostays.branch.service.HotelBranchPhotoService;
import jakarta.validation.Valid;import org.springframework.http.MediaType;import org.springframework.security.access.prepost.PreAuthorize;import org.springframework.web.bind.annotation.*;import org.springframework.web.multipart.MultipartFile;
@RestController @RequestMapping("/api")
public class HotelBranchPhotoController{
 private final HotelBranchPhotoService s;private final CurrentUserProvider c;
 public HotelBranchPhotoController(HotelBranchPhotoService s,CurrentUserProvider c){this.s=s;this.c=c;}
 @PreAuthorize("hasAnyRole('ROOT_ADMIN','MANAGER')") @PostMapping(value="/root-admin/hotel-branches/{branchId}/photos",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
 public ApiResponse<HotelBranchPhotoResponse> upload(@PathVariable Long branchId,@RequestPart MultipartFile file,@RequestPart(required=false)String caption,@RequestPart BranchPhotoType photoType,@RequestParam(defaultValue="false")boolean primaryPhoto){
  return ApiResponse.success("Branch photo uploaded",s.upload(branchId,new HotelBranchPhotoRequest(caption,photoType,primaryPhoto),file,c.userId()),"unknown");
 }
 @GetMapping("/hotel-branches/{branchId}/photos") public ApiResponse<java.util.List<HotelBranchPhotoResponse>> list(@PathVariable Long branchId){return ApiResponse.success("Branch photos",s.list(branchId),"unknown");}
 @PreAuthorize("hasAnyRole('ROOT_ADMIN','MANAGER')") @DeleteMapping("/root-admin/hotel-branches/{branchId}/photos/{photoId}") public ApiResponse<Void> delete(@PathVariable Long branchId,@PathVariable Long photoId){s.delete(branchId,photoId,c.userId());return ApiResponse.success("Photo deleted",null,"unknown");}
}
