package com.gdgcgc.iCollege.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "complaint_comments")
public class ComplaintComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;

    @Column(nullable = false)
    private String text;

    private LocalDateTime createdAt = LocalDateTime.now();


    @ManyToOne
    @JoinColumn(name = "scholarId", nullable = false)
    private UserInfo user;

    // Link to the Complaint this comment belongs to
    @ManyToOne
    @JoinColumn(name = "complaintId", nullable = false)
    private Complaint complaint;
}
