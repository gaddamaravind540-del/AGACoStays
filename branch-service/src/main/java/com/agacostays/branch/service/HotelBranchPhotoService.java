package com.agacostays.branch.service;
import com.agacostays.branch.dto.request.HotelBranchPhotoRequest; import com.agacostays.branch.dto.response.HotelBranchPhotoResponse;
import org.springframework.web.multipart.MultipartFile; import java.util.*;
public interface HotelBranchPhotoService{
 HotelBranchPhotoResponse upload(Long branchId,HotelBranchPhotoRequest r,MultipartFile file,Long actor);
 List<HotelBranchPhotoResponse> list(Long branchId);
 void delete(Long branchId,Long photoId,Long actor);
}
