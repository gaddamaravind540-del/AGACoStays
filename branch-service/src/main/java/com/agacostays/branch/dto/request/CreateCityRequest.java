package com.agacostays.branch.dto.request;
import jakarta.validation.constraints.*;
public record CreateCityRequest(@NotBlank String cityName,@NotBlank String state,@NotBlank String country) {}
