package com.agacostays.room.validation;

import org.springframework.stereotype.Component;

@Component
public class RoomAvailabilityValidationService {
    public void validateDates(java.time.LocalDate in, java.time.LocalDate out){ com.agacostays.room.util.DateRangeUtil.validate(in,out); }
}
