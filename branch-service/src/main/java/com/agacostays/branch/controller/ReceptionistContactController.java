package com.agacostays.branch.controller;
import com.agacostays.branch.dto.request.ReceptionistContactRequest;import com.agacostays.branch.dto.response.*;import com.agacostays.branch.security.CurrentUserProvider;import com.agacostays.branch.service.ReceptionistContactService;
import jakarta.validation.Valid;import org.springframework.security.access.prepost.PreAuthorize;import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api")
public class ReceptionistContactController{
 private final ReceptionistContactService s;private final CurrentUserProvider c;
 public ReceptionistContactController(ReceptionistContactService s,CurrentUserProvider c){this.s=s;this.c=c;}
 @PreAuthorize("hasRole('MANAGER')") @PostMapping("/manager/hotel-branches/{branchId}/receptionist-contacts") public ApiResponse<ReceptionistContactResponse> add(@PathVariable Long branchId,@Valid @RequestBody ReceptionistContactRequest r){return ApiResponse.success("Receptionist contact added",s.add(branchId,r,c.userId()),"unknown");}
 @GetMapping("/hotel-branches/{branchId}/contacts") public ApiResponse<java.util.List<ReceptionistContactResponse>> list(@PathVariable Long branchId){return ApiResponse.success("Receptionist contacts",s.list(branchId),"unknown");}
 @GetMapping("/hotel-branches/{branchId}/receptionists") public ApiResponse<java.util.List<ReceptionistContactResponse>> listReceptionists(@PathVariable Long branchId){return ApiResponse.success("Receptionist contacts",s.list(branchId),"unknown");}
}
