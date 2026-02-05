package com.gdgcgc.iCollege.dtos;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String scholarId;
    private String otp;
    private String newPassword;
}