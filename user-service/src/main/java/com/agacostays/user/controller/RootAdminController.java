package com.agacostays.user.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agacostays.user.dto.request.CreateManagerRequest;
import com.agacostays.user.dto.request.UpdateManagerRequest;
import com.agacostays.user.dto.response.ApiResponse;
import com.agacostays.user.dto.response.ManagerResponse;
import com.agacostays.user.dto.response.PageResponse;
import com.agacostays.user.dto.response.UserStatusResponse;
import com.agacostays.user.security.CurrentUserProvider;
import com.agacostays.user.service.RootAdminService;

import jakarta.validation.Valid;

@PreAuthorize("hasRole('ROOT_ADMIN')")
@RestController
@RequestMapping("/api/root-admin")
public class RootAdminController {
	private final RootAdminService service;
	private final CurrentUserProvider current;

	public RootAdminController(RootAdminService s, CurrentUserProvider c) {
		service = s;
		current = c;
	}

	@PostMapping("/managers")
	public ApiResponse<ManagerResponse> create(@Valid @RequestBody CreateManagerRequest r) {
		return ApiResponse.success("Manager created", service.createManager(r, current.userId()), "unknown");
	}

	@GetMapping("/managers")
	public ApiResponse<PageResponse<ManagerResponse>> list(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		return ApiResponse.success("Managers", service.listManagers(page, size), "unknown");
	}

	@GetMapping("/managers/{id}")
	public ApiResponse<ManagerResponse> get(@PathVariable Long id) {
		return ApiResponse.success("Manager", service.getManager(id), "unknown");
	}

	@PutMapping("/managers/{id}")
	public ApiResponse<ManagerResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateManagerRequest r) {
		return ApiResponse.success("Manager updated", service.updateManager(id, r, current.userId()), "unknown");
	}

	@PutMapping("/managers/{id}/status")
	public ApiResponse<UserStatusResponse> status(@PathVariable Long id, @RequestParam boolean active) {
		return ApiResponse.success("Manager status updated", service.updateManagerStatus(id, active, current.userId()),
				"unknown");
	}

	@DeleteMapping("/managers/{id}")
	public ApiResponse<Void> delete(@PathVariable Long id) {
		service.deleteManager(id, current.userId());
		return ApiResponse.success("Manager deactivated", null, "unknown");
	}
}
