package com.agacostays.notification.mapper;

import com.agacostays.notification.dto.response.CheckoutReminderResponse;
import com.agacostays.notification.entity.CheckoutReminder;
import org.springframework.stereotype.Component;

@Component
public class CheckoutReminderMapper {
    public CheckoutReminderResponse toResponse(CheckoutReminder r) {
        return new CheckoutReminderResponse(r.getReminderId(), r.getBranchId(), r.getBookingId(), r.getCustomerId(), r.getCheckoutDate(), r.getCheckoutTime(), r.getReminderBeforeHours(), r.getScheduledTime(), r.getSentTime(), r.getStatus());
    }
}
