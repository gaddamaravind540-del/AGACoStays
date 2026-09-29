package com.agacostays.room.consumer;

import com.agacostays.room.enums.RoomStatus;
import com.agacostays.room.repository.RoomRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * The source document names this consumer but does not define its payload.
 * The assumed payload contains bookingId, branchId and roomId.
 */
@Component
@Slf4j
public class CheckoutCompletedEventConsumer {

    private final RoomRepository roomRepository;

    public CheckoutCompletedEventConsumer(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @KafkaListener(topics = "costays.booking.events", groupId = "room-service")
    public void onCheckoutCompleted(CheckoutCompletedEvent event) {
        if (event == null || event.getRoomId() == null || event.getBranchId() == null) return;
        roomRepository.findByRoomIdAndBranchId(event.getRoomId(), event.getBranchId()).ifPresent(room -> {
            room.setStatus(RoomStatus.AVAILABLE);
            roomRepository.save(room);
            log.info("Checkout completed. Room {} marked AVAILABLE", room.getRoomId());
        });
    }

    @Getter @Setter @NoArgsConstructor
    public static class CheckoutCompletedEvent {
        private Long bookingId;
        private Long branchId;
        private Long roomId;
    }
}
