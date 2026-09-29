package com.agacostays.analytics.scheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component @Slf4j
public class ProjectionRebuildScheduler {
    @Scheduled(cron="0 0 3 * * *")
    public void rebuild(){
        log.info("Analytics projection rebuild window executed; projections are event-driven and idempotent.");
    }
}
