package com.gdgcgc.iCollege.entities;


import com.gdgcgc.iCollege.entities.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) private Long id;
    private String name;
    private String publicName;
    private String email;
    private String password;
    @Column(unique = true) private String scholarId;

    @Column(nullable = false)
    private boolean isVerified = false;


    @Enumerated(EnumType.STRING) private Role role;

}
