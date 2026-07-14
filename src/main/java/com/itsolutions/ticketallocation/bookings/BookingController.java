package com.itsolutions.ticketallocation.bookings;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse bookSeat(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody CreateBookingRequest request) {

        // TODO: Replace X-User-Id with authenticated user after JWT integration
        return bookingService.bookSeat(userId, request);
    }
}