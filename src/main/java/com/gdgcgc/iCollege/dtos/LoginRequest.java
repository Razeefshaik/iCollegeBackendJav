package com.gdgcgc.iCollege.dtos;

import lombok.Data;

@Data
public class LoginRequest {
    private String scholarId;
    private String password;
}