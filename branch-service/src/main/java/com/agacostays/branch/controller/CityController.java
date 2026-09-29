package com.agacostays.branch.controller;
import com.agacostays.branch.dto.request.*;import com.agacostays.branch.dto.response.*;import com.agacostays.branch.security.CurrentUserProvider;import com.agacostays.branch.service.CityService;
import jakarta.validation.Valid;import org.springframework.security.access.prepost.PreAuthorize;import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api")
public class CityController{
 private final CityService s;private final CurrentUserProvider c;
 public CityController(CityService s,CurrentUserProvider c){this.s=s;this.c=c;}
 @PreAuthorize("hasRole('ROOT_ADMIN')") @PostMapping("/root-admin/cities") public ApiResponse<CityResponse> create(@Valid @RequestBody CreateCityRequest r){return ok(s.create(r,c.userId()),"City created");}
 @GetMapping("/cities") public ApiResponse<java.util.List<CityResponse>> list(){return ok(s.activeCities(),"Cities");}
 @GetMapping("/cities/{id}") public ApiResponse<CityResponse> get(@PathVariable Long id){return ok(s.get(id),"City");}
 @PreAuthorize("hasRole('ROOT_ADMIN')") @PutMapping("/root-admin/cities/{id}") public ApiResponse<CityResponse> update(@PathVariable Long id,@Valid @RequestBody UpdateCityRequest r){return ok(s.update(id,r,c.userId()),"City updated");}
 @PreAuthorize("hasRole('ROOT_ADMIN')") @DeleteMapping("/root-admin/cities/{id}") public ApiResponse<Void> delete(@PathVariable Long id){s.deactivate(id,c.userId());return ok(null,"City deactivated");}
 private <T>ApiResponse<T> ok(T d,String m){return ApiResponse.success(m,d,"unknown");}
}
