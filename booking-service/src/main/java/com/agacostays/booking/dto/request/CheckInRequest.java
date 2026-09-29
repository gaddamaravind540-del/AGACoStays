package com.agacostays.booking.dto.request;

import com.agacostays.booking.enums.GuestIdProofType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CheckInRequest {

    @NotNull
    private GuestIdProofType guestIdProofType;

    @NotBlank
    private String guestIdProofNumber;

    private String guestName;
}
