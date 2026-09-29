package com.agacostays.notification.service;

import com.agacostays.notification.dto.request.CheckoutReminderRequest;
import com.agacostays.notification.dto.response.CheckoutReminderResponse;
import com.agacostays.notification.entity.CheckoutReminder;

public interface CheckoutReminderService {
    CheckoutReminderResponse schedule(CheckoutReminderRequest request);
    void cancel(Long reminderId);
    void processDueReminders();
    CheckoutReminder load(Long id);
    CheckoutReminder getForBooking(Long bookingId, Long customerId);
    com.agacostays.notification.dto.response.EmailResponse sendForBooking(Long bookingId, Long branchId, Long customerId, String email);
}
