package com.gdgcgc.iCollege.controllers;

import com.gdgcgc.iCollege.dtos.AuthResponse;
import com.gdgcgc.iCollege.dtos.LoginRequest;
import com.gdgcgc.iCollege.dtos.RegisterRequest;
import com.gdgcgc.iCollege.services.UserAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    private final UserAuthService authService;

    public UserAuthController(UserAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}