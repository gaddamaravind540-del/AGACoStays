package com.agacostays.room.controller;

import com.agacostays.room.dto.response.ApiResponse;
import com.agacostays.room.dto.response.RoomPhotoResponse;
import com.agacostays.room.service.RoomPhotoService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomPhotoController {
    private final RoomPhotoService service;
    public RoomPhotoController(RoomPhotoService service) { this.service = service; }

    @PostMapping(value = "/manager/hotel-branches/{branchId}/rooms/{roomId}/photos", consumes = "multipart/form-data")
    @PreAuthorize("hasAnyRole('MANAGER','RECEPTIONIST')")
    public ApiResponse<List<RoomPhotoResponse>> upload(@PathVariable Long branchId, @PathVariable Long roomId,
                                                        @RequestPart("photos") List<MultipartFile> photos,
                                                        @RequestParam(required = false) String caption,
                                                        @RequestParam(defaultValue = "false") boolean isPrimary) {
        return ApiResponse.success("Room photos uploaded successfully", service.upload(branchId, roomId, photos, caption, isPrimary));
    }

    @GetMapping("/hotel-branches/{branchId}/rooms/{roomId}/photos")
    public ApiResponse<List<RoomPhotoResponse>> list(@PathVariable Long branchId, @PathVariable Long roomId) {
        return ApiResponse.success("Room photos fetched successfully", service.list(branchId, roomId));
    }

    @DeleteMapping("/manager/hotel-branches/{branchId}/rooms/{roomId}/photos/{photoId}")
    @PreAuthorize("hasAnyRole('MANAGER','RECEPTIONIST')")
    public ApiResponse<Void> delete(@PathVariable Long branchId, @PathVariable Long roomId, @PathVariable Long photoId) {
        service.delete(branchId, roomId, photoId);
        return ApiResponse.success("Room photo deleted successfully", null);
    }

    @PutMapping("/manager/hotel-branches/{branchId}/rooms/{roomId}/photos/{photoId}")
    @PreAuthorize("hasAnyRole('MANAGER','RECEPTIONIST')")
    public ApiResponse<RoomPhotoResponse> updateCaption(@PathVariable Long branchId, @PathVariable Long roomId,
                                                         @PathVariable Long photoId, @RequestParam @NotBlank String caption) {
        return ApiResponse.success("Room photo updated successfully", service.updateCaption(branchId, roomId, photoId, caption));
    }

    @PutMapping(value = "/manager/hotel-branches/{branchId}/rooms/{roomId}/photos/{photoId}/replace", consumes = "multipart/form-data")
    @PreAuthorize("hasAnyRole('MANAGER','RECEPTIONIST')")
    public ApiResponse<RoomPhotoResponse> replace(@PathVariable Long branchId, @PathVariable Long roomId, @PathVariable Long photoId,
                                                   @RequestPart("photo") MultipartFile photo, @RequestParam(required = false) String caption) {
        return ApiResponse.success("Room photo replaced successfully", service.replace(branchId, roomId, photoId, photo, caption));
    }

    @PutMapping("/manager/hotel-branches/{branchId}/rooms/{roomId}/photos/{photoId}/primary")
    @PreAuthorize("hasAnyRole('MANAGER','RECEPTIONIST')")
    public ApiResponse<RoomPhotoResponse> primary(@PathVariable Long branchId, @PathVariable Long roomId, @PathVariable Long photoId) {
        return ApiResponse.success("Primary photo updated successfully", service.setPrimary(branchId, roomId, photoId));
    }
}
