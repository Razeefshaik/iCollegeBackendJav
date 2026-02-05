package com.gdgcgc.iCollege.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your iCollege Verification Code");
        message.setText("Your OTP for registration is: " + otp + "\n\nThis code expires in 10 minutes.");
        mailSender.send(message);
    }

    public void sendPasswordResetEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Reset Your Password - iCollege");
        message.setText("Someone requested to reset your password.\n\n" +
                "Your OTP is: " + otp + "\n\n" +
                "If this wasn't you, please ignore this email.");
        mailSender.send(message);
    }
}
