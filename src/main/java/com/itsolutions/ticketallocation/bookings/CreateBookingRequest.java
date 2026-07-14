package com.itsolutions.ticketallocation.bookings;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookingRequest {

    @NotNull(message = "Seat ID is required")
    private Long seatId;
}