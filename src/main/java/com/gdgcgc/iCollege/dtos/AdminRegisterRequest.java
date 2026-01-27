package com.gdgcgc.iCollege.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRegisterRequest {
    private String name;
    private String publicName;
    private String email;
    private String password;
    private String scholarId;
    private String passkey; // The secret key for admin authorization
}
