package com.itsolutions.ticketallocation.events;


import com.itsolutions.ticketallocation.seats.Seat;
import com.itsolutions.ticketallocation.seats.SeatGenerationService;
import com.itsolutions.ticketallocation.seats.SeatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final SeatRepository seatRepository;
    private final SeatGenerationService seatGenerationService;

    @Transactional
    public EventResponse createEvent(CreateEventRequest request) {

        Event savedEvent = saveEvent(request);

        List<Seat> seats =
                seatGenerationService.generateSeats(savedEvent, request);

        seatRepository.saveAll(seats);

        return EventResponse.builder()
                .id(savedEvent.getId())
                .title(savedEvent.getTitle())
                .venue(savedEvent.getVenue())
                .eventDate(savedEvent.getEventDate())
                .totalSeats(seats.size())
                .build();
    }

    private Event saveEvent(CreateEventRequest request) {

        Event event = Event.builder()
                .title(request.getTitle())
                .venue(request.getVenue())
                .eventDate(request.getEventDate())
                .build();

        return eventRepository.save(event);
    }
}