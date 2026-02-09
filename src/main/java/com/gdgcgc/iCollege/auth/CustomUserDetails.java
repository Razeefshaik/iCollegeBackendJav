package com.gdgcgc.iCollege.auth;


import com.gdgcgc.iCollege.entities.UserInfo;
import com.gdgcgc.iCollege.entities.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private final UserInfo user;
    public CustomUserDetails(UserInfo user) { this.user = user; }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getRole() != null) {
            // This maps Role.STUDENT -> "ROLE_STUDENT" and Role.ADMIN -> "ROLE_ADMIN"
            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        }
        return authorities;
    }

    @Override public String getPassword() { return user.getPassword(); }
    @Override public String getUsername() { return user.getScholarId(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() {
        return true;
    }
}