package com.agacostays.room.producer;

import com.agacostays.room.constants.KafkaTopicConstants;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RoomEventProducer {
    private final KafkaTemplate<String,Object> kafkaTemplate;
    public RoomEventProducer(KafkaTemplate<String,Object> kafkaTemplate) { this.kafkaTemplate = kafkaTemplate; }
    public void publish(Object event, String key) {
        kafkaTemplate.send(KafkaTopicConstants.ROOM_EVENTS, key, event);
    }
}
