package com.agacostays.user.controller;
import com.agacostays.user.dto.request.UpdateProfileRequest; import com.agacostays.user.dto.response.*;
import com.agacostays.user.security.CurrentUserProvider; import com.agacostays.user.service.ProfileService; import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
@RestController @RequestMapping("/api/profile")
public class ProfileController {
 private final ProfileService s; private final CurrentUserProvider c;
 public ProfileController(ProfileService s,CurrentUserProvider c){this.s=s;this.c=c;}
 @PreAuthorize("isAuthenticated()")
 @GetMapping("/me") public ApiResponse<UserProfileResponse> me(){return ApiResponse.success("Profile",s.getMe(c.userId()),"unknown");}
 @PreAuthorize("isAuthenticated()")
 @PutMapping("/me") public ApiResponse<UserProfileResponse> update(@Valid @RequestBody UpdateProfileRequest r){return ApiResponse.success("Profile updated",s.updateMe(c.userId(),r),"unknown");}
}
