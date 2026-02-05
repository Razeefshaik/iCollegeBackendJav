package com.gdgcgc.iCollege.controllers;

import com.gdgcgc.iCollege.dtos.*;
import com.gdgcgc.iCollege.services.UserAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    private final UserAuthService authService;

    public UserAuthController(UserAuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getMyProfile(Principal principal) {
        // principal.getName() returns the scholarId because of your UserDetails config
        return ResponseEntity.ok(authService.getMyProfile(principal.getName()));
    }

    @PostMapping("/register-admin")
    public ResponseEntity<String> registerAdmin(@RequestBody AdminRegisterRequest request) {
        return ResponseEntity.ok(authService.registerAdmin(request));
    }

    @PostMapping("/verify")
    public ResponseEntity<AuthResponse> verify(@RequestParam String scholarId, @RequestParam String otp) {
        return ResponseEntity.ok(authService.verifyOtp(scholarId, otp));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}