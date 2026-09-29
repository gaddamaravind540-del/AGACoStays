package com.agacostays.restaurant.producer;

import com.agacostays.restaurant.constants.KafkaTopicConstants;
import com.agacostays.restaurant.entity.RestaurantOrder;
import com.agacostays.restaurant.event.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RestaurantEventProducer {
    private final KafkaTemplate<String,Object> kafka;

    public RestaurantEventProducer(KafkaTemplate<String,Object> kafka) {
        this.kafka = kafka;
    }

    public void placed(RestaurantOrder o) {
        kafka.send(KafkaTopicConstants.FOOD_ORDER_PLACED, String.valueOf(o.getOrderId()),
                new FoodOrderPlacedEvent(o.getOrderId(), o.getBranchId(), o.getRestaurantId(), o.getBookingId(), o.getCustomerId()));
    }

    public void accepted(RestaurantOrder o) {
        kafka.send(KafkaTopicConstants.FOOD_ORDER_ACCEPTED, String.valueOf(o.getOrderId()),
                new FoodOrderAcceptedEvent(o.getOrderId(), o.getBranchId(), o.getRestaurantId(), o.getBookingId(), o.getCustomerId()));
    }

    public void rejected(RestaurantOrder o) {
        kafka.send(KafkaTopicConstants.FOOD_ORDER_REJECTED, String.valueOf(o.getOrderId()),
                new FoodOrderRejectedEvent(o.getOrderId(), o.getBranchId(), o.getRestaurantId(), o.getBookingId(), o.getCustomerId()));
    }

    public void ready(RestaurantOrder o) {
        kafka.send(KafkaTopicConstants.FOOD_READY, String.valueOf(o.getOrderId()),
                new FoodReadyEvent(o.getOrderId(), o.getBranchId(), o.getRestaurantId(), o.getBookingId(), o.getCustomerId()));
    }

    public void delivered(RestaurantOrder o) {
        kafka.send(KafkaTopicConstants.FOOD_DELIVERED, String.valueOf(o.getOrderId()),
                new FoodDeliveredEvent(o.getOrderId(), o.getBranchId(), o.getRestaurantId(), o.getBookingId(), o.getCustomerId()));
    }

    public void cancelled(RestaurantOrder o) {
        kafka.send(KafkaTopicConstants.FOOD_ORDER_CANCELLED, String.valueOf(o.getOrderId()),
                new FoodOrderCancelledEvent(o.getOrderId(), o.getBranchId(), o.getRestaurantId(), o.getBookingId(), o.getCustomerId()));
    }
}
