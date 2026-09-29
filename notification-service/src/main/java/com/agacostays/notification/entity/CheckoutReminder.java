package com.agacostays.notification.entity;

import com.agacostays.notification.enums.ReminderStatus;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="checkout_reminders", indexes={
        @Index(name="idx_reminder_schedule", columnList="status,scheduled_time"),
        @Index(name="idx_reminder_booking", columnList="booking_id")
})
public class CheckoutReminder {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="reminder_id") private Long reminderId;
    @Column(name="branch_id", nullable=false) private Long branchId;
    @Column(name="booking_id", nullable=false) private Long bookingId;
    @Column(name="customer_id", nullable=false) private Long customerId;
    @Column(name="checkout_date", nullable=false) private LocalDate checkoutDate;
    @Column(name="checkout_time", nullable=false) private String checkoutTime;
    @Column(name="reminder_before_hours", nullable=false) private Integer reminderBeforeHours;
    @Column(name="scheduled_time", nullable=false) private LocalDateTime scheduledTime;
    @Column(name="sent_time") private LocalDateTime sentTime;
    @Enumerated(EnumType.STRING) @Column(nullable=false, length=30) private ReminderStatus status;
    @Column(name="created_at", nullable=false) private LocalDateTime createdAt;
    @Column(name="updated_at", nullable=false) private LocalDateTime updatedAt;
    @PrePersist void prePersist(){var now=LocalDateTime.now(); if(createdAt==null)createdAt=now; updatedAt=now; if(status==null)status=ReminderStatus.SCHEDULED;}
    @PreUpdate void preUpdate(){updatedAt=LocalDateTime.now();}
    public Long getReminderId(){return reminderId;} public void setReminderId(Long v){reminderId=v;}
    public Long getBranchId(){return branchId;} public void setBranchId(Long v){branchId=v;}
    public Long getBookingId(){return bookingId;} public void setBookingId(Long v){bookingId=v;}
    public Long getCustomerId(){return customerId;} public void setCustomerId(Long v){customerId=v;}
    public LocalDate getCheckoutDate(){return checkoutDate;} public void setCheckoutDate(LocalDate v){checkoutDate=v;}
    public String getCheckoutTime(){return checkoutTime;} public void setCheckoutTime(String v){checkoutTime=v;}
    public Integer getReminderBeforeHours(){return reminderBeforeHours;} public void setReminderBeforeHours(Integer v){reminderBeforeHours=v;}
    public LocalDateTime getScheduledTime(){return scheduledTime;} public void setScheduledTime(LocalDateTime v){scheduledTime=v;}
    public LocalDateTime getSentTime(){return sentTime;} public void setSentTime(LocalDateTime v){sentTime=v;}
    public ReminderStatus getStatus(){return status;} public void setStatus(ReminderStatus v){status=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
    public LocalDateTime getUpdatedAt(){return updatedAt;} public void setUpdatedAt(LocalDateTime v){updatedAt=v;}
}
