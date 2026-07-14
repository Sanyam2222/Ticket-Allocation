package com.itsolutions.ticketallocation.seats;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @GetMapping("/{eventId}/seats")
    public List<SeatResponse> getSeatsByEvent(
            @PathVariable Long eventId) {

        return seatService.getSeatsByEvent(eventId);
    }
}