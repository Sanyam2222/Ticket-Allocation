package com.itsolutions.ticketallocation.seats;


import com.itsolutions.ticketallocation.events.CreateEventRequest;
import com.itsolutions.ticketallocation.events.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SeatGenerationService {

    public List<Seat> generateSeats(
            Event event,
            CreateEventRequest request) {

        List<Seat> seats = new ArrayList<>();

        for (int row = 0; row < request.getRows(); row++) {

            char rowLetter = (char) ('A' + row);

            for (int seatNo = 1;
                 seatNo <= request.getSeatsPerRow();
                 seatNo++) {

                seats.add(
                        Seat.builder()
                                .seatNumber(rowLetter + String.valueOf(seatNo))
                                .status(SeatStatus.AVAILABLE)
                                .event(event)
                                .build()
                );
            }
        }

        log.info("Generated {} seats", seats.size());

        return seats;
    }
}
