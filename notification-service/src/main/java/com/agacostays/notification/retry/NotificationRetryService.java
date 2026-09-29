package com.agacostays.notification.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationRetryService {
    private static final Logger log=LoggerFactory.getLogger(NotificationRetryService.class);
    public boolean shouldRetry(int attempt, int maxAttempts){return attempt < maxAttempts;}
    public void recordRetry(Long notificationId,int attempt,String reason){log.warn("notification retry notificationId={} attempt={} reason={}",notificationId,attempt,reason);}
}
