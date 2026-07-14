package com.itsolutions.ticketallocation.seats;

import com.itsolutions.ticketallocation.Exception.ResourceNotFoundException;
import com.itsolutions.ticketallocation.events.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;
    private final EventRepository eventRepository;

    public List<SeatResponse> getSeatsByEvent(Long eventId) {

        if (!eventRepository.existsById(eventId)) {
            throw new ResourceNotFoundException("Event not found");
        }

        List<Seat> seats =
                seatRepository.findByEventIdOrderBySeatNumber(eventId);

        return seats.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private SeatResponse mapToResponse(Seat seat) {

        return SeatResponse.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .status(seat.getStatus())
                .build();
    }
}
