package com.gdgcgc.iCollege.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
@Table(name="announcements")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    @Column(length = 2000)
    private String content;
    private String category; // e.g., Academic, Sports, Hostel
    private String imageUrl;
    private boolean isUrgent = false;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "admin_scholar_id", referencedColumnName = "scholarId")
    private UserInfo admin;
}
