package com.gdgcgc.iCollege.dtos.complaintDTOs;

import com.gdgcgc.iCollege.entities.enums.ComplaintCategory;
import com.gdgcgc.iCollege.entities.enums.ComplaintStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintResponse {

    private Long complaintId;
    private String title;
    private String description;
    private String imageUrl;
    private ComplaintStatus status;
    private ComplaintCategory category;
    private int upvotes;
    private LocalDateTime createdAt;
    private String createdBy;
}
