package com.itsolutions.ticketallocation.Exception;

public class SeatAlreadyBookedException extends ResourceNotFoundException {
    public SeatAlreadyBookedException(String message) {
        super(message);
    }
}
