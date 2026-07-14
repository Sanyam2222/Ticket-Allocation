package com.itsolutions.ticketallocation.events;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class EventResponse {

    private Long id;

    private String title;

    private String venue;

    private LocalDateTime eventDate;

    private int totalSeats;
}