package com.agacostays.support.scheduler;

import com.agacostays.support.entity.CustomerSupportRequest;
import com.agacostays.support.enums.SupportPriority;
import com.agacostays.support.enums.SupportStatus;
import com.agacostays.support.exception.SupportEscalationException;
import com.agacostays.support.repository.CustomerSupportRequestRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Component
public class SupportEscalationScheduler {

    private final CustomerSupportRequestRepository repo;

    public SupportEscalationScheduler(CustomerSupportRequestRepository repo) {
        this.repo=repo;
    }

    @Scheduled(fixedDelay = 300000)
    @Transactional
    public void escalateOpenRequests() {
        List<CustomerSupportRequest> open = repo.findByStatusIn(
                List.of(SupportStatus.OPEN, SupportStatus.IN_PROGRESS));

        OffsetDateTime threshold = OffsetDateTime.now().minusHours(24);

        for (CustomerSupportRequest request : open) {
            if (request.getCreatedAt() != null
                    && request.getCreatedAt().isBefore(threshold)
                    && request.getPriority() != SupportPriority.URGENT) {
                request.setPriority(SupportPriority.URGENT);
                repo.save(request);
            }
        }
    }
}
