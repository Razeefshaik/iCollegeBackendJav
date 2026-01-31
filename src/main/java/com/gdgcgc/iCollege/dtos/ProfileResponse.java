package com.gdgcgc.iCollege.dtos;

import com.gdgcgc.iCollege.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileResponse {
    private String name;
    private String publicName;
    private String email;
    private String scholarId;
    private Role role;
}