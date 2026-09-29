package com.agacostays.user.controller;
import com.agacostays.user.dto.request.*; import com.agacostays.user.dto.response.*;
import com.agacostays.user.security.CurrentUserProvider; import com.agacostays.user.service.RootAdminService;
import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*; import java.util.*;
@PreAuthorize("hasRole('ROOT_ADMIN')")
@RestController @RequestMapping("/api/root-admin")
public class RootAdminController {
 private final RootAdminService service; private final CurrentUserProvider current;
 public RootAdminController(RootAdminService s,CurrentUserProvider c){service=s;current=c;}
 @PostMapping("/managers") public ApiResponse<ManagerResponse> create(@Valid @RequestBody CreateManagerRequest r){return ApiResponse.success("Manager created",service.createManager(r,current.userId()),"unknown");}
 @GetMapping("/managers") public ApiResponse<PageResponse<ManagerResponse>> list(@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="20")int size){return ApiResponse.success("Managers",service.listManagers(page,size),"unknown");}
 @GetMapping("/managers/{id}") public ApiResponse<ManagerResponse> get(@PathVariable Long id){return ApiResponse.success("Manager",service.getManager(id),"unknown");}
 @PutMapping("/managers/{id}") public ApiResponse<ManagerResponse> update(@PathVariable Long id,@Valid @RequestBody UpdateManagerRequest r){return ApiResponse.success("Manager updated",service.updateManager(id,r,current.userId()),"unknown");}
 @PutMapping("/managers/{id}/status") public ApiResponse<UserStatusResponse> status(@PathVariable Long id,@RequestParam boolean active){return ApiResponse.success("Manager status updated",service.updateManagerStatus(id,active,current.userId()),"unknown");}
 @DeleteMapping("/managers/{id}") public ApiResponse<Void> delete(@PathVariable Long id){service.deleteManager(id,current.userId());return ApiResponse.success("Manager deactivated",null,"unknown");}
}
