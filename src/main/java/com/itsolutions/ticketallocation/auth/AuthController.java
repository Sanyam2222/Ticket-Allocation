package com.itsolutions.ticketallocation.auth;


import com.itsolutions.ticketallocation.Security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        System.out.println("working");
        return authService.login(request);
    }

    @PostMapping("/register")
    public RegisterResponse register(
            @RequestBody @Valid RegisterRequest request) {

        return authService.register(request);
    }
}
