package com.agacostays.user.controller;
import com.agacostays.user.dto.request.CreatePermissionRequest; import com.agacostays.user.dto.response.*; import com.agacostays.user.security.CurrentUserProvider;
import com.agacostays.user.service.PermissionService; import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*; import java.util.List;
@PreAuthorize("hasRole('ROOT_ADMIN')")
@RestController @RequestMapping("/api/permissions")
public class PermissionController {
 private final PermissionService s; private final CurrentUserProvider c;
 public PermissionController(PermissionService s,CurrentUserProvider c){this.s=s;this.c=c;}
 @PostMapping public ApiResponse<PermissionResponse> create(@Valid @RequestBody CreatePermissionRequest r){return ApiResponse.success("Permission created",s.create(r,c.userId()),"unknown");}
 @GetMapping public ApiResponse<List<PermissionResponse>> list(){return ApiResponse.success("Permissions",s.list(),"unknown");}
}
