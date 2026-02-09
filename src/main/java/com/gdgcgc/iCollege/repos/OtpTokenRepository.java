package com.gdgcgc.iCollege.repos;

import com.gdgcgc.iCollege.entities.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// src/main/java/com/gdgcgc/iCollege/repos/OtpTokenRepository.java
@Repository
public interface OtpTokenRepository extends JpaRepository<OtpToken, Long> {
    Optional<OtpToken> findByScholarId(String scholarId);
}