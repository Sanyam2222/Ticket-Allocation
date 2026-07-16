package com.itsolutions.ticketallocation.Exception;

public class SeatNotFoundException extends ResourceNotFoundException {
    public SeatNotFoundException(String message) {
        super(message);
    }
}
