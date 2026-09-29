package com.agacostays.branch.dto.request;
import com.agacostays.branch.enums.BranchPhotoType;
import jakarta.validation.constraints.NotNull;
public record HotelBranchPhotoRequest(String caption,@NotNull BranchPhotoType photoType,boolean primaryPhoto) {}
