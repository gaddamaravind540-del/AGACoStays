package com.agacostays.analytics.consumer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.agacostays.analytics.event.listener.AnalyticsKafkaEventListener;
class AnalyticsConsumerTest{@Test void load(){assertNotNull(AnalyticsKafkaEventListener.class);}}
