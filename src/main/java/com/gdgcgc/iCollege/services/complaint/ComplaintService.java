package com.gdgcgc.iCollege.services.complaint;


import com.gdgcgc.iCollege.dtos.complaintDTOs.ComplaintRequest;
import com.gdgcgc.iCollege.dtos.complaintDTOs.ComplaintResponse;
import com.gdgcgc.iCollege.entities.Complaint;
import com.gdgcgc.iCollege.entities.UserInfo;
import com.gdgcgc.iCollege.repos.ComplaintRepository;
import com.gdgcgc.iCollege.repos.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;

    public ComplaintService(ComplaintRepository complaintRepository, UserRepository userRepository) {
        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
    }

    public ComplaintResponse createComplaint(String scholarId, ComplaintRequest complaintRequest){

        UserInfo user= userRepository.findByScholarId(scholarId).orElseThrow(()->new RuntimeException("User not found"));

        Complaint complaint= new Complaint();
        complaint.setTitle(complaintRequest.getTitle());
        complaint.setDescription(complaintRequest.getDescription());
        complaint.setImageUrl(complaintRequest.getImageUrl());
        complaint.setCategory(complaintRequest.getCategory());
        complaint.setUser(user);
        Complaint savedComplaint= complaintRepository.save(complaint);

        return mapToResponse(savedComplaint);
    }

    public Page<ComplaintResponse> getAllComplaints(int page, int size){

        Pageable pageable= PageRequest.of(page,size, Sort.by("createdAt").descending());

        Page<Complaint> complaints= complaintRepository.findAll(pageable);

        return complaints.map(this::mapToResponse);
    }

    public List<ComplaintResponse> getMyComplaints(String scholarId) {
        UserInfo user = userRepository.findByScholarId(scholarId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //
        return complaintRepository.findByUserId(user.getId()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ComplaintResponse getComplaintById(Long id) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found with ID: " + id));
        return mapToResponse(complaint);
    }

    public ComplaintResponse upvoteComplaint(Long complaintId, String scholarId){

     UserInfo user= userRepository.findByScholarId(scholarId).orElseThrow(()->new RuntimeException("User not found"));
     Complaint complaint= complaintRepository.findById(complaintId).orElseThrow(()->new RuntimeException("User not found"));

     int count= complaintRepository.hasUserUpvoted(complaintId,scholarId);

     if(count>0){
         complaintRepository.removeUpvote(complaintId,user.getScholarId());

     }else{
         complaintRepository.addUpvote(complaintId,user.getScholarId());
     }

        return mapToResponse(complaint);

    }

    private ComplaintResponse mapToResponse(Complaint c){

        int upvotes= complaintRepository.countUpvotes(c.getComplaintId());
        return new ComplaintResponse(
                c.getComplaintId(),
                c.getTitle(),
                c.getDescription(),
                c.getImageUrl(),
                c.getStatus(),
                c.getCategory(),
                upvotes,
                c.getCreatedAt(),
                c.getUser().getName()
        );
    }
}
