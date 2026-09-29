package com.agacostays.branch.service;
import com.agacostays.branch.dto.request.*; import com.agacostays.branch.dto.response.*; import java.util.*;
public interface HotelBranchService{
 HotelBranchResponse create(HotelBranchRequest r,Long actor);
 List<HotelBranchSummaryResponse> activeBranches();
 HotelBranchResponse get(Long id);
 List<HotelBranchSummaryResponse> byCity(Long cityId);
 HotelBranchResponse update(Long id,UpdateHotelBranchRequest r,Long actor);
 HotelBranchResponse status(Long id,UpdateBranchStatusRequest r,Long actor);
 void deactivate(Long id,Long actor);
 BranchPublicResponse publicDetails(Long id);
 List<HotelBranchSummaryResponse> rootAdminAll();
}
