package com.agacostays.room.validation;

import org.springframework.stereotype.Component;

@Component
public class RoomPriceValidationService {
    public void validatePrice(java.math.BigDecimal price){ com.agacostays.room.util.RoomPriceUtil.validate(price); }
}
