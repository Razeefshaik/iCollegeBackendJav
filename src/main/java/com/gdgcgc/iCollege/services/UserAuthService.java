package com.gdgcgc.iCollege.services;

import com.gdgcgc.iCollege.auth.CustomUserDetails;
import com.gdgcgc.iCollege.auth.JWTService;
import com.gdgcgc.iCollege.dtos.AuthResponse;
import com.gdgcgc.iCollege.dtos.LoginRequest;
import com.gdgcgc.iCollege.dtos.RegisterRequest;
import com.gdgcgc.iCollege.entities.UserInfo;
import com.gdgcgc.iCollege.entities.enums.Role;
import com.gdgcgc.iCollege.repos.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserAuthService(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JWTService jwtService,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        // 1. Check if user already exists
        if (userRepository.existsByScholarId(request.getScholarId())) {
            throw new RuntimeException("User with this Scholar ID already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User with this Email already exists");
        }

        // 2. Create the User Entity
        UserInfo user = new UserInfo();
        user.setName(request.getName());
        user.setPublicName(request.getPublicName());
        user.setEmail(request.getEmail());
        user.setScholarId(request.getScholarId());
        user.setRole(Role.USER); // Default role is USER

        // 3. Encrypt the password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // 4. Save to DB
        userRepository.save(user);

        // 5. Generate Token immediately so they don't have to login again
        String token = jwtService.generateToken(new CustomUserDetails(user));
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        // 1. Authenticate using Spring Security Manager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getScholarId(),
                        request.getPassword()
                )
        );

        // 2. Fetch User to generate token
        UserInfo user = userRepository.findByScholarId(request.getScholarId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Generate Token
        String token = jwtService.generateToken(new CustomUserDetails(user));
        return new AuthResponse(token);
    }
}