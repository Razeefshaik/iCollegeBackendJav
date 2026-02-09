package com.gdgcgc.iCollege.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@Table(name="polls")
public class OpinionPoll {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String question;
    @ElementCollection
    private List<String> options; // e.g., ["Yes", "No"]

    private LocalDateTime expiresAt;
    private boolean isAnonymous = true;

    // Track votes: Option Index -> List of Scholar IDs
    @ElementCollection
    private Map<Integer, Integer> voteCounts = new HashMap<>();
}