package com.agacostays.branch.mapper;
import com.agacostays.branch.dto.response.*;
import com.agacostays.branch.entity.HotelBranch;
import org.springframework.stereotype.Component;
@Component public class HotelBranchMapper {
 public HotelBranchResponse toResponse(HotelBranch b){
  return new HotelBranchResponse(b.getBranchId(),b.getCity().getCityId(),b.getCity().getCityName(),b.getBranchName(),b.getAddress(),
   b.getLandmark(),b.getLatitude()==null?null:b.getLatitude().toPlainString(),b.getLongitude()==null?null:b.getLongitude().toPlainString(),
   b.getPhone(),b.getEmail(),b.getDescription(),b.getStatus().name());
 }
 public HotelBranchSummaryResponse toSummary(HotelBranch b){
  return new HotelBranchSummaryResponse(b.getBranchId(),b.getBranchName(),b.getCity().getCityName(),b.getAddress(),b.getStatus().name());
 }
}
