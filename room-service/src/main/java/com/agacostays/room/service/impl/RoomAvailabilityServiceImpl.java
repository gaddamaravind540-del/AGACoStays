package com.agacostays.room.service.impl;

import com.agacostays.room.client.BookingServiceClient;
import com.agacostays.room.dto.request.RoomAvailabilityRequest;
import com.agacostays.room.dto.response.BookingStatusResponse;
import com.agacostays.room.dto.response.RoomAvailabilityResponse;
import com.agacostays.room.entity.Room;
import com.agacostays.room.enums.RoomAvailabilityStatus;
import com.agacostays.room.enums.RoomStatus;
import com.agacostays.room.repository.RoomRepository;
import com.agacostays.room.util.DateRangeUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoomAvailabilityServiceImpl implements com.agacostays.room.service.RoomAvailabilityService {

    private final RoomRepository roomRepository;
    private final BookingServiceClient bookingServiceClient;

    public RoomAvailabilityServiceImpl(RoomRepository roomRepository, BookingServiceClient bookingServiceClient) {
        this.roomRepository = roomRepository;
        this.bookingServiceClient = bookingServiceClient;
    }

    @Override
    public List<RoomAvailabilityResponse> search(Long branchId, RoomAvailabilityRequest request) {
        DateRangeUtil.validate(request.checkInDate(), request.checkOutDate());
        List<Room> rooms = roomRepository.findByBranchId(branchId, PageRequest.of(0, 100)).getContent();
        List<RoomAvailabilityResponse> result = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getStatus() == RoomStatus.MAINTENANCE) continue;
            boolean availableByStatus = room.getStatus() == RoomStatus.AVAILABLE;
            boolean bookingExists = false;
            String reason = availableByStatus ? "Room is available" : "Room status is " + room.getStatus();
            try {
                BookingStatusResponse booking = bookingServiceClient.checkRoomAvailability(room.getRoomId(), request.checkInDate(), request.checkOutDate());
                bookingExists = booking != null && booking.bookingExists();
                if (bookingExists) reason = "An existing booking overlaps the requested dates";
            } catch (Exception ex) {
                reason = availableByStatus ? "Room is available; booking service could not be checked" : reason;
            }
            boolean available = availableByStatus && !bookingExists;
            result.add(new RoomAvailabilityResponse(room.getRoomId(), branchId, room.getRoomNumber(),
                    available ? RoomAvailabilityStatus.AVAILABLE : RoomAvailabilityStatus.NOT_AVAILABLE,
                    request.checkInDate(), request.checkOutDate(), room.getCurrentPricePerDay(), reason));
        }
        return result;
    }
}
