package com.gdgcgc.iCollege.services;

import com.gdgcgc.iCollege.auth.CustomUserDetails;
import com.gdgcgc.iCollege.auth.JWTService;
import com.gdgcgc.iCollege.dtos.*;
import com.gdgcgc.iCollege.entities.OtpToken;
import com.gdgcgc.iCollege.entities.UserInfo;
import com.gdgcgc.iCollege.entities.enums.Role;
import com.gdgcgc.iCollege.repos.OtpTokenRepository;
import com.gdgcgc.iCollege.repos.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class UserAuthService {

    @Value("${admin.registration.passkey}")
    private String secretPasskey;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final OtpTokenRepository otpTokenRepository;
    private final EmailService emailService;

    public UserAuthService(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JWTService jwtService,
                           AuthenticationManager authenticationManager,
                           OtpTokenRepository otpTokenRepository,
                           EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.otpTokenRepository=otpTokenRepository;
        this.emailService=emailService;
    }


    // In UserAuthService.java

    public ProfileResponse getMyProfile(String scholarId) {
        UserInfo user = userRepository.findByScholarId(scholarId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new ProfileResponse(
                user.getName(),
                user.getPublicName(),
                user.getEmail(),
                user.getScholarId(),
                user.getRole()
        );
    }


    public String register(RegisterRequest request) {
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
        user.setRole(Role.STUDENT); // Default role is USER
        user.setVerified(false);
        // 3. Encrypt the password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // 4. Save to DB
        userRepository.save(user);

        // Generate and Send OTP
        String otp = String.valueOf(new Random().nextInt(900000) + 100000); // 6-digit OTP
        OtpToken token = new OtpToken(request.getEmail(), otp);
        otpTokenRepository.save(token);

        emailService.sendOtpEmail(request.getEmail(), otp);

        return "Registration successful. Please check your email for OTP.";

    }


    public AuthResponse verifyOtp(String email, String otp) {
        OtpToken otpToken = otpTokenRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid Email"));

        if (!otpToken.getOtp().equals(otp) || LocalDateTime.now().isAfter(otpToken.getExpiresAt())) {
            throw new RuntimeException("Invalid or Expired OTP");
        }

        // Activate User
        UserInfo user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setVerified(true);
        userRepository.save(user);

        // Clean up OTP
        otpTokenRepository.delete(otpToken);

        // Return JWT
        String token = jwtService.generateToken(new CustomUserDetails(user));
        return new AuthResponse(token);
    }


    public AuthResponse registerAdmin(AdminRegisterRequest request) {
        // 1. Verify the secret passkey
        if (request.getPasskey() == null || !request.getPasskey().equals(secretPasskey)) {
            throw new RuntimeException("Unauthorized: Invalid Admin Passkey");
        }

        // 2. Check if user already exists
        Optional<UserInfo> existingUser = userRepository.findByScholarId(request.getScholarId());
        UserInfo user;

        if (existingUser.isPresent()) {
            // Update existing user to ADMIN role
            user = existingUser.get();
        } else {
            // Create new Admin user
            user = new UserInfo();
            user.setScholarId(request.getScholarId());
            user.setEmail(request.getEmail());
        }

        // 3. Update/Set fields
        user.setName(request.getName());
        user.setPublicName(request.getPublicName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ADMIN); // Set role to ADMIN

        // 4. Save to DB
        userRepository.save(user);

        // 5. Generate Token
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