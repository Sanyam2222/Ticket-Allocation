//package com.itsolutions.ticketallocation.seeder;
//
//import com.itsolutions.ticketallocation.bookings.BookingRepository;
//import com.itsolutions.ticketallocation.events.EventRepository;
//import com.itsolutions.ticketallocation.seats.SeatRepository;
//import com.itsolutions.ticketallocation.users.CreateUserRequest;
//import com.itsolutions.ticketallocation.users.UserRepository;
//import com.itsolutions.ticketallocation.users.UserService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//
//
//@Component
//@Profile("dev")
//@RequiredArgsConstructor
//@Slf4j
//public class DevDataInitializer implements CommandLineRunner {
//
//
//    private final PasswordEncoder passwordEncoder;
//
//    private final UserRepository userRepository;
//    private final EventRepository eventRepository;
//    private final SeatRepository seatRepository;
//    private final BookingRepository bookingRepository;
//    private final UserService userService;
//
//    @Override
//    public void run(String... args) {
//
//        if (userRepository.count() > 0) {
//            log.info("Development data already exists. Skipping initialization.");
//            return;
//        }
//
//        log.info("Initializing development data...");
//
//        createUsers();
//
//        log.info("Development data initialized successfully.");
//    }
//
//    private void createUsers() {
//
//        if (userRepository.count() > 0) {
//            log.info("Users already exist. Skipping...");
//            return;
//        }
//
//        log.info("Creating 100 users...");
//
//        for (int i = 1; i <= 100; i++) {
//
//            CreateUserRequest request = new CreateUserRequest();
//            request.setName("User " + i);
//            request.setEmail("user" + i + "@test.com");
//            request.setPassword(passwordEncoder.encode("123456"));
//
//            userService.createUser(request);
//        }
//
//        log.info("100 users created.");
//    }
//}
//
