package com.gdgcgc.iCollege.controllers;


import com.gdgcgc.iCollege.dtos.AnnouncementDTOS.AnnouncementRequest;
import com.gdgcgc.iCollege.dtos.AnnouncementDTOS.AnnouncementResponse;
import com.gdgcgc.iCollege.services.AnnouncementService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("announcements")
public class AnnouncementController {

    private final AnnouncementService  announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/publish")
    public ResponseEntity<AnnouncementResponse> publish(@RequestBody AnnouncementRequest request, Principal principal) {
        return ResponseEntity.ok(announcementService.createAnnouncement(principal.getName(), request));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AnnouncementResponse>> getAll() {
        return ResponseEntity.ok(announcementService.getAllAnnouncements());
    }


}
