package com.gdgcgc.iCollege.dtos;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String publicName;
    private String email;
    private String password;
    private String scholarId;
}