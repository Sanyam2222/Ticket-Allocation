package com.itsolutions.ticketallocation.bookings;

import com.itsolutions.ticketallocation.Exception.ResourceNotFoundException;
import com.itsolutions.ticketallocation.Exception.SeatAlreadyBookedException;
import com.itsolutions.ticketallocation.Exception.SeatNotFoundException;
import com.itsolutions.ticketallocation.seats.Seat;
import com.itsolutions.ticketallocation.seats.SeatRepository;
import com.itsolutions.ticketallocation.seats.SeatStatus;
import com.itsolutions.ticketallocation.users.User;
import com.itsolutions.ticketallocation.users.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingTransactionService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public BookingResponse doBookSeat(Long userId,
                                      CreateBookingRequest request) {

        String requestId = UUID.randomUUID().toString().substring(0, 8);

        String tag = String.format(
                "[%s][%s][user=%d]",
                requestId,
                Thread.currentThread().getName(),
                userId
        );

        log.info("ENTER seatId={} {}", request.getSeatId(), tag);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Seat seat = seatRepository.findById(request.getSeatId())
                .orElseThrow(() -> new SeatNotFoundException("Seat not found"));

        log.info("CHECKED status={} {}", seat.getStatus(), tag);

        if (seat.getStatus() == SeatStatus.BOOKED) {

            log.warn("REJECTED_BY_APP {}", tag);

            throw new SeatAlreadyBookedException("Seat already booked");
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        seat.setStatus(SeatStatus.BOOKED);

        seatRepository.save(seat);

        log.info("INSERTING {}", tag);

        Booking booking = Booking.builder()
                .bookingTime(LocalDateTime.now())
                .user(user)
                .seat(seat)
                .build();

        booking = bookingRepository.save(booking);

        log.info("COMMITTED bookingId={} {}", booking.getId(), tag);

        return BookingResponse.builder()
                .bookingId(booking.getId())
                .eventTitle(booking.getSeat().getEvent().getTitle())
                .seatNumber(booking.getSeat().getSeatNumber())
                .bookingTime(booking.getBookingTime())
                .build();
    }
}
