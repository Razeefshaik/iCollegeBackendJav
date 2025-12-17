package com.gdgcgc.iCollege.auth;

import com.gdgcgc.iCollege.entities.UserInfo;
import com.gdgcgc.iCollege.repos.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;
    public CustomUserDetailsService(UserRepository repo){
        this.repo= repo;
    }

    @Override
    public UserDetails loadUserByUsername(String scholarId){
        Optional<UserInfo> user = repo.findByScholarId(scholarId);
        if (user.isPresent()) {
            return new CustomUserDetails(user.get());
        } else {
            throw new UsernameNotFoundException("User not found with scholarId: " + scholarId);
        }

    }
}

