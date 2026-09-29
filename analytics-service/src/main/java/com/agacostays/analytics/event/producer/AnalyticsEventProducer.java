package com.agacostays.analytics.event.producer;
import com.agacostays.analytics.event.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
@Component
public class AnalyticsEventProducer {
    private final KafkaTemplate<String,Object> kafka;
    public AnalyticsEventProducer(KafkaTemplate<String,Object> kafka){this.kafka=kafka;}
    public void projectionUpdated(AnalyticsProjectionUpdatedEvent e){kafka.send("analytics.events",String.valueOf(e.branchId()),e);}
    public void reportExported(ReportExportedEvent e){kafka.send("analytics.events",String.valueOf(e.reportId()),e);}
}
