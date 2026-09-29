package com.agacostays.branch.dto.request;
import jakarta.validation.constraints.*;
import java.time.LocalTime;
public record ReceptionistContactRequest(Long staffId,@NotBlank String phone,String alternatePhone,@Email String email,
 String shift,LocalTime availableFrom,LocalTime availableTo,String purpose) {}
