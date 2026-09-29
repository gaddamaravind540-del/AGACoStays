package com.agacostays.notification.scheduler;

import com.agacostays.notification.service.CheckoutReminderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CheckoutReminderScheduler {
    private final CheckoutReminderService service;
    public CheckoutReminderScheduler(CheckoutReminderService service){this.service=service;}
    @Scheduled(fixedDelayString="${notification.scheduler.fixed-delay-ms:60000}")
    public void run(){service.processDueReminders();}
}
