package com.itsolutions.ticketallocation.bookings;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse bookSeat(
            Authentication authentication,
            @Valid @RequestBody CreateBookingRequest request) {

        Long userId = Long.parseLong(authentication.getName());

        return bookingService.bookSeat(userId, request);
    }
}