package com.gdgcgc.iCollege.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class OtpToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String scholarId;
    private String otp;
    private LocalDateTime expiresAt;

    public OtpToken(String scholarId, String otp) {
        this.scholarId = scholarId;
        this.otp = otp;
        this.expiresAt = LocalDateTime.now().plusMinutes(10); // OTP valid for 10 mins
    }
}
