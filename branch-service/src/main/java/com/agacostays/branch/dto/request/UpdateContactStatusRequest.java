package com.agacostays.branch.dto.request;
import com.agacostays.branch.enums.ContactAvailabilityStatus;
import jakarta.validation.constraints.NotNull;
public record UpdateContactStatusRequest(@NotNull ContactAvailabilityStatus status) {}
