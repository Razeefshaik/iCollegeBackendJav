package com.gdgcgc.iCollege.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${mail.sender}")
    private String senderEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String to, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(senderEmail); // <--- CRITICAL FIX
            message.setTo(to);
            message.setSubject("Your iCollege Verification Code");
            message.setText("Your OTP for registration is: " + otp + "\n\nThis code expires in 10 minutes.");

            mailSender.send(message);
            System.out.println("✅ SUCCESS: OTP sent to " + to);
        } catch (Exception e) {
            System.err.println("❌ ERROR: " + e.getMessage());
        }
    }

    public void sendPasswordResetEmail(String to, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(senderEmail); // <--- CRITICAL FIX
            message.setTo(to);
            message.setSubject("Reset Your Password - iCollege");
            message.setText("Someone requested to reset your password.\n\nOTP: " + otp);

            mailSender.send(message);
            System.out.println("✅ SUCCESS: Reset OTP sent to " + to);
        } catch (Exception e) {
            System.err.println("❌ ERROR: " + e.getMessage());
        }
    }
}
