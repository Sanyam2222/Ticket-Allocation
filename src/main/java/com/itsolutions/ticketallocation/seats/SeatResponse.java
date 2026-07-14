package com.itsolutions.ticketallocation.seats;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SeatResponse {

    private Long id;

    private String seatNumber;

    private SeatStatus status;
}