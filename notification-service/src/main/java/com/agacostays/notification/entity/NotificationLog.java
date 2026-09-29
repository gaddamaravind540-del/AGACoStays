package com.agacostays.notification.entity;

import com.agacostays.notification.enums.NotificationType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="notification_logs", indexes={
        @Index(name="idx_notification_customer", columnList="customer_id,created_at"),
        @Index(name="idx_notification_branch", columnList="branch_id,created_at"),
        @Index(name="idx_notification_booking", columnList="booking_id")
})
public class NotificationLog {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="notification_id")
    private Long notificationId;
    @Column(name="branch_id") private Long branchId;
    @Column(name="customer_id") private Long customerId;
    @Column(name="booking_id") private Long bookingId;
    @Enumerated(EnumType.STRING) @Column(name="notification_type", nullable=false) private NotificationType notificationType;
    @Column(nullable=false, length=200) private String title;
    @Column(nullable=false, columnDefinition="text") private String message;
    @Column(name="email_to", length=320) private String emailTo;
    @Column(name="email_status", length=30) private String emailStatus;
    @Column(name="is_read", nullable=false) private boolean read;
    @Column(name="sent_at") private LocalDateTime sentAt;
    @Column(name="created_at", nullable=false) private LocalDateTime createdAt;

    @PrePersist void prePersist() { if (createdAt == null) createdAt = LocalDateTime.now(); }
    public Long getNotificationId(){return notificationId;} public void setNotificationId(Long v){notificationId=v;}
    public Long getBranchId(){return branchId;} public void setBranchId(Long v){branchId=v;}
    public Long getCustomerId(){return customerId;} public void setCustomerId(Long v){customerId=v;}
    public Long getBookingId(){return bookingId;} public void setBookingId(Long v){bookingId=v;}
    public NotificationType getNotificationType(){return notificationType;} public void setNotificationType(NotificationType v){notificationType=v;}
    public String getTitle(){return title;} public void setTitle(String v){title=v;}
    public String getMessage(){return message;} public void setMessage(String v){message=v;}
    public String getEmailTo(){return emailTo;} public void setEmailTo(String v){emailTo=v;}
    public String getEmailStatus(){return emailStatus;} public void setEmailStatus(String v){emailStatus=v;}
    public boolean isRead(){return read;} public void setRead(boolean v){read=v;}
    public LocalDateTime getSentAt(){return sentAt;} public void setSentAt(LocalDateTime v){sentAt=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}
