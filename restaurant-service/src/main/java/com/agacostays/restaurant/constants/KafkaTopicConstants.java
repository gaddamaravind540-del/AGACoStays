package com.agacostays.restaurant.constants;
public final class KafkaTopicConstants {
    private KafkaTopicConstants() {}
    public static final String FOOD_ORDER_PLACED = "food-order-placed";
    public static final String FOOD_ORDER_ACCEPTED = "food-order-accepted";
    public static final String FOOD_ORDER_REJECTED = "food-order-rejected";
    public static final String FOOD_READY = "food-ready";
    public static final String FOOD_DELIVERED = "food-delivered";
    public static final String FOOD_ORDER_CANCELLED = "food-order-cancelled";
}
