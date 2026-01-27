package com.gdgcgc.iCollege.dtos.AnnouncementDTOS;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AnnouncementResponse {
    private Long id;
    private String title;
    private String content;
    private String category;
    private String imageUrl;
    private boolean isUrgent;
    private LocalDateTime createdAt;
    private String adminName;
}
