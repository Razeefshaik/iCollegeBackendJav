package com.gdgcgc.iCollege.dtos.complaintDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ComplaintCommentResponse {

    private Long id;
    private String text;
    private String userName; // Just the name, not the full user object
    private LocalDateTime createdAt;
}
