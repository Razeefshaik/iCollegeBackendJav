package com.gdgcgc.iCollege.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gdgcgc.iCollege.entities.enums.ComplaintCategory;
import com.gdgcgc.iCollege.entities.enums.ComplaintStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long complaintId;

    private String title;

    @Column(length = 1000)
    private String description;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private ComplaintStatus status = ComplaintStatus.PENDING; // Default is PENDING

    @Enumerated(EnumType.STRING)
    private ComplaintCategory category;

    //private int upvotes = 0;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "complaint_upvotes", joinColumns = @JoinColumn(name = "complaintId"), inverseJoinColumns = @JoinColumn(name = "scholarId" , referencedColumnName = "scholarId"))
    private Set<UserInfo> upvoters;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "scholarId", referencedColumnName = "scholarId")
    private UserInfo user;


}
