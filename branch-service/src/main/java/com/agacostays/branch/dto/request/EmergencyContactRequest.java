package com.agacostays.branch.dto.request;
import com.agacostays.branch.enums.EmergencyContactPurpose;
import jakarta.validation.constraints.*;
public record EmergencyContactRequest(@NotBlank String contactName,@NotBlank String phone,String alternatePhone,
 @Email String email,@NotNull EmergencyContactPurpose purpose) {}
