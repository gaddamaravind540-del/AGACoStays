package com.agacostays.payment.client; import lombok.extern.slf4j.Slf4j; import org.springframework.stereotype.Component;
@Slf4j @Component public class NotificationServiceClient { public void notifyPayment(Long customerId,String message){log.info("Notification queued customerId={} message={}",customerId,message);} }
