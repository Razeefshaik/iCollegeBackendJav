package com.gdgcgc.iCollege.services;


import com.gdgcgc.iCollege.dtos.AnnouncementDTOS.AnnouncementRequest;
import com.gdgcgc.iCollege.dtos.AnnouncementDTOS.AnnouncementResponse;
import com.gdgcgc.iCollege.entities.Announcement;
import com.gdgcgc.iCollege.entities.UserInfo;
import com.gdgcgc.iCollege.repos.AnnouncementRepository;
import com.gdgcgc.iCollege.repos.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository, UserRepository userRepository){
        this.announcementRepository=announcementRepository;
        this.userRepository=userRepository;
    }


    public AnnouncementResponse createAnnouncement(String adminScholarId, AnnouncementRequest request) {
        UserInfo admin = userRepository.findByScholarId(adminScholarId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        Announcement announcement = new Announcement();
        announcement.setTitle(request.getTitle());
        announcement.setContent(request.getContent());
        announcement.setCategory(request.getCategory());
        announcement.setImageUrl(request.getImageUrl());
        announcement.setUrgent(request.isUrgent());
        announcement.setAdmin(admin);

        Announcement saved = announcementRepository.save(announcement);
        return mapToResponse(saved);
    }


    public List<AnnouncementResponse> getAllAnnouncements() {

        return announcementRepository.findAllByOrderByCreatedAtDesc().stream().map(this::mapToResponse).collect(Collectors.toList());
    }
    private AnnouncementResponse mapToResponse(Announcement a) {
        return new AnnouncementResponse(
                a.getId(), a.getTitle(), a.getContent(),
                a.getCategory(), a.getImageUrl(), a.isUrgent(),
                a.getCreatedAt(), a.getAdmin().getName()
        );
    }


}
