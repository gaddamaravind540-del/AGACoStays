package com.agacostays.notification.producer;

import com.agacostays.notification.event.NotificationFailedEvent;
import com.agacostays.notification.event.NotificationSentEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class NotificationEventProducer {
    private final KafkaTemplate<String,Object> kafka;
    public NotificationEventProducer(KafkaTemplate<String,Object> kafka){this.kafka=kafka;}
    public void sent(NotificationSentEvent event){safeSend("notification.sent",String.valueOf(event.notificationId()),event);}
    public void failed(NotificationFailedEvent event){safeSend("notification.failed",String.valueOf(event.notificationId()),event);}
    private void safeSend(String topic,String key,Object value){try{kafka.send(topic,key,value);}catch(Exception ignored){}}
}
