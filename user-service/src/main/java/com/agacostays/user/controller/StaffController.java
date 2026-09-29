package com.agacostays.user.controller;
import com.agacostays.user.dto.response.*; import com.agacostays.user.security.CurrentUserProvider; import com.agacostays.user.service.StaffService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
@RestController @RequestMapping("/api/staff")
public class StaffController {
 private final StaffService s; private final CurrentUserProvider c;
 public StaffController(StaffService s,CurrentUserProvider c){this.s=s;this.c=c;}
 @PreAuthorize("hasAnyRole('RECEPTIONIST','RESTAURANT_ADMIN','CHEF','SERVING_STAFF','HOUSEKEEPING_STAFF')")
 @GetMapping("/my-branches") public ApiResponse<java.util.List<StaffBranchMappingResponse>> branches(){return ApiResponse.success("Assigned branches",s.getMyBranchMappings(c.userId()),"unknown");}
}
