package com.agacostays.branch.controller;
import com.agacostays.branch.dto.request.*;import com.agacostays.branch.dto.response.*;import com.agacostays.branch.security.CurrentUserProvider;import com.agacostays.branch.service.HotelBranchService;
import jakarta.validation.Valid;import org.springframework.security.access.prepost.PreAuthorize;import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api")
public class HotelBranchController{
 private final HotelBranchService s;private final CurrentUserProvider c;
 public HotelBranchController(HotelBranchService s,CurrentUserProvider c){this.s=s;this.c=c;}
 @PreAuthorize("hasRole('ROOT_ADMIN')") @PostMapping("/root-admin/hotel-branches") public ApiResponse<HotelBranchResponse> create(@Valid @RequestBody HotelBranchRequest r){return ok(s.create(r,c.userId()),"Branch created");}
 @GetMapping("/hotel-branches") public ApiResponse<java.util.List<HotelBranchSummaryResponse>> list(){return ok(s.activeBranches(),"Branches");}
 @GetMapping("/hotel-branches/{id}") public ApiResponse<HotelBranchResponse> get(@PathVariable Long id){return ok(s.get(id),"Branch");}
 @GetMapping("/hotel-branches/city/{cityId}") public ApiResponse<java.util.List<HotelBranchSummaryResponse>> byCity(@PathVariable Long cityId){return ok(s.byCity(cityId),"Branches by city");}
 @PreAuthorize("hasRole('ROOT_ADMIN')") @PutMapping("/root-admin/hotel-branches/{id}") public ApiResponse<HotelBranchResponse> update(@PathVariable Long id,@Valid @RequestBody UpdateHotelBranchRequest r){return ok(s.update(id,r,c.userId()),"Branch updated");}
 @PreAuthorize("hasRole('ROOT_ADMIN')") @PutMapping("/root-admin/hotel-branches/{id}/status") public ApiResponse<HotelBranchResponse> status(@PathVariable Long id,@Valid @RequestBody UpdateBranchStatusRequest r){return ok(s.status(id,r,c.userId()),"Branch status updated");}
 @PreAuthorize("hasRole('ROOT_ADMIN')") @DeleteMapping("/root-admin/hotel-branches/{id}") public ApiResponse<Void> delete(@PathVariable Long id){s.deactivate(id,c.userId());return ok(null,"Branch deactivated");}
 @PreAuthorize("hasRole('ROOT_ADMIN')") @GetMapping("/root-admin/hotel-branches") public ApiResponse<java.util.List<HotelBranchSummaryResponse>> all(){return ok(s.rootAdminAll(),"All branches");}
 @PreAuthorize("hasRole('ROOT_ADMIN')") @GetMapping("/root-admin/hotel-branches/{id}/complete-details") public ApiResponse<BranchPublicResponse> complete(@PathVariable Long id){return ok(s.publicDetails(id),"Complete branch details");}
 private <T>ApiResponse<T> ok(T d,String m){return ApiResponse.success(m,d,"unknown");}
}
