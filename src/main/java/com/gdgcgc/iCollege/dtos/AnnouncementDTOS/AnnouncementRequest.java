package com.gdgcgc.iCollege.dtos.AnnouncementDTOS;

import lombok.Data;

@Data
public class AnnouncementRequest {
    private String title;
    private String content;
    private String category;
    private String imageUrl;
    private boolean isUrgent;
}
