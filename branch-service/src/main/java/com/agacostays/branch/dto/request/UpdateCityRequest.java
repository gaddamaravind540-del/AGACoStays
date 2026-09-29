package com.agacostays.branch.dto.request;
import jakarta.validation.constraints.*;
import com.agacostays.branch.enums.CityStatus;
public record UpdateCityRequest(@NotBlank String cityName,@NotBlank String state,@NotBlank String country,@NotNull CityStatus status) {}
