package com.itsolutions.ticketallocation.bookings;



import com.itsolutions.ticketallocation.Exception.ResourceNotFoundException;
import com.itsolutions.ticketallocation.Exception.SeatAlreadyBookedException;
import com.itsolutions.ticketallocation.seats.Seat;
import com.itsolutions.ticketallocation.seats.SeatRepository;
import com.itsolutions.ticketallocation.seats.SeatStatus;
import com.itsolutions.ticketallocation.users.User;
import com.itsolutions.ticketallocation.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public BookingResponse bookSeat(Long userId,
                                    CreateBookingRequest request) {


        String requestId = UUID.randomUUID().toString().substring(0, 8);

        String tag = String.format(
                "[%s][%s][user=%d]",
                requestId,
                Thread.currentThread().getName(),
                userId
        );

        log.info("ENTER seatId={} {}", request.getSeatId(), tag);

        User user = getUser(userId);

        Seat seat = getSeat(request.getSeatId());

        log.info("CHECKED status={} {}", seat.getStatus(), tag);

        validateSeatAvailability(seat,tag);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        markSeatAsBooked(seat);

        log.info("INSERTING {}", tag);

        Booking booking = createBooking(user, seat);
        log.info("COMMITTED bookingId={} {}", booking.getId(), tag);

        return mapToResponse(booking);
    }

    private User getUser(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    private Seat getSeat(Long seatId) {

        return seatRepository.findById(seatId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Seat not found"));
    }

    private void validateSeatAvailability(Seat seat, String tag) {

        if (seat.getStatus() == SeatStatus.BOOKED) {

            log.warn("REJECTED_BY_APP {}", tag);

            throw new SeatAlreadyBookedException("Seat already booked");
        }
    }

    private void markSeatAsBooked(Seat seat) {
        seat.setStatus(SeatStatus.BOOKED);
    }

    private Booking createBooking(User user, Seat seat) {

        Booking booking = Booking.builder()
                .bookingTime(LocalDateTime.now())
                .user(user)
                .seat(seat)
                .build();

        return bookingRepository.save(booking);
    }

    private BookingResponse mapToResponse(Booking booking) {

        return BookingResponse.builder()
                .bookingId(booking.getId())
                .eventTitle(booking.getSeat().getEvent().getTitle())
                .seatNumber(booking.getSeat().getSeatNumber())
                .bookingTime(booking.getBookingTime())
                .build();
    }
}