package com.agacostays.branch.dto.request;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record HotelBranchRequest(
 @NotNull Long cityId,@NotBlank String branchName,@NotBlank String address,String landmark,
 @DecimalMin("-90.0") @DecimalMax("90.0") BigDecimal latitude,
 @DecimalMin("-180.0") @DecimalMax("180.0") BigDecimal longitude,
 String phone,@Email String email,String description) {}
