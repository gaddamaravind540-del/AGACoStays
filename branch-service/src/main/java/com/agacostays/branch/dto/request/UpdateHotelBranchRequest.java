package com.agacostays.branch.dto.request;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record UpdateHotelBranchRequest(
 @NotNull Long cityId,@NotBlank String branchName,@NotBlank String address,String landmark,
 BigDecimal latitude,BigDecimal longitude,String phone,@Email String email,String description) {}
