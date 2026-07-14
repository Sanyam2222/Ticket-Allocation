package com.itsolutions.ticketallocation.bookings;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BookingResponse {

    private Long bookingId;

    private String eventTitle;

    private String seatNumber;

    private LocalDateTime bookingTime;
}
