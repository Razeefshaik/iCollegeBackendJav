package com.gdgcgc.iCollege.dtos.complaintDTOs;


import com.gdgcgc.iCollege.entities.enums.ComplaintCategory;
import lombok.Data;

@Data
public class ComplaintRequest {

    private String title;
    private String description;
    private String imageUrl;
    private ComplaintCategory category;
}
