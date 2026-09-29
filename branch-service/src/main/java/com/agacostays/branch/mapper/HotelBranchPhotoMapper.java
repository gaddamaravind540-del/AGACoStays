package com.agacostays.branch.mapper;
import com.agacostays.branch.dto.response.HotelBranchPhotoResponse;
import com.agacostays.branch.entity.HotelBranchPhoto;
import org.springframework.stereotype.Component;
@Component public class HotelBranchPhotoMapper {
 public HotelBranchPhotoResponse toResponse(HotelBranchPhoto p){
  return new HotelBranchPhotoResponse(p.getPhotoId(),p.getBranch().getBranchId(),p.getPhotoUrl(),p.getCaption(),p.getPhotoType().name(),p.isPrimaryPhoto());
 }
}
