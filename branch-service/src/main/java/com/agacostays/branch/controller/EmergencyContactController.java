package com.agacostays.branch.controller;
import com.agacostays.branch.dto.request.EmergencyContactRequest;import com.agacostays.branch.dto.response.*;import com.agacostays.branch.security.CurrentUserProvider;import com.agacostays.branch.service.EmergencyContactService;
import jakarta.validation.Valid;import org.springframework.security.access.prepost.PreAuthorize;import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api")
public class EmergencyContactController{
 private final EmergencyContactService s;private final CurrentUserProvider c;
 public EmergencyContactController(EmergencyContactService s,CurrentUserProvider c){this.s=s;this.c=c;}
 @PreAuthorize("hasRole('MANAGER')") @PostMapping("/manager/hotel-branches/{branchId}/emergency-contacts") public ApiResponse<EmergencyContactResponse> add(@PathVariable Long branchId,@Valid @RequestBody EmergencyContactRequest r){return ApiResponse.success("Emergency contact added",s.add(branchId,r,c.userId()),"unknown");}
 @GetMapping("/hotel-branches/{branchId}/emergency-contacts") public ApiResponse<java.util.List<EmergencyContactResponse>> list(@PathVariable Long branchId){return ApiResponse.success("Emergency contacts",s.list(branchId),"unknown");}
}
