package com.agacostays.room.validation;

import org.springframework.stereotype.Component;

@Component
public class RoomStatusValidationService {
    public void validateStatus(com.agacostays.room.enums.RoomStatus status){ if(status==null) throw new IllegalArgumentException("status is required"); }
}
