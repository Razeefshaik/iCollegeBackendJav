package com.gdgcgc.iCollege.services.complaint;


import com.gdgcgc.iCollege.dtos.complaintDTOs.ComplaintCommentRequest;
import com.gdgcgc.iCollege.dtos.complaintDTOs.ComplaintCommentResponse;
import com.gdgcgc.iCollege.entities.Complaint;
import com.gdgcgc.iCollege.entities.ComplaintComment;
import com.gdgcgc.iCollege.entities.UserInfo;
import com.gdgcgc.iCollege.repos.ComplaintCommentRepository;
import com.gdgcgc.iCollege.repos.ComplaintRepository;
import com.gdgcgc.iCollege.repos.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplaintCommentService {

    private final ComplaintCommentRepository complaintCommentRepository;
    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;

    public ComplaintCommentService(ComplaintCommentRepository complaintCommentRepository, ComplaintRepository complaintRepository, UserRepository userRepository) {
        this.complaintCommentRepository = complaintCommentRepository;
        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
    }


    public ComplaintCommentResponse addComment(Long complaintId, ComplaintCommentRequest complaintCommentRequest){

        Complaint complaint= complaintRepository.findById(complaintId).orElseThrow(()->new RuntimeException("Complaint not found"));
        UserInfo user= userRepository.findByScholarId(complaint.getUser().getScholarId()).orElseThrow(()->new RuntimeException("User not found"));

        ComplaintComment comment= new ComplaintComment();
        comment.setComplaint(complaint);
        comment.setUser(user);
        comment.setText(complaintCommentRequest.getText());
        comment.setCreatedAt(LocalDateTime.now());

        ComplaintComment savedComment= complaintCommentRepository.save(comment);

        return new ComplaintCommentResponse(
                savedComment.getCommentId(),
                savedComment.getText(),
                user.getName(),
                savedComment.getCreatedAt()
        );
    }

    public List<ComplaintCommentResponse> getComments(Long complaintId) {

        List<ComplaintComment> comments;
        try {
            comments = complaintCommentRepository.findByComplaintComplaintIdOrderByCreatedAtDesc(complaintId);
        } catch (Exception e) {
            throw new RuntimeException("Complaint not found");
        }

        return comments.stream().map(c -> new ComplaintCommentResponse(
                c.getCommentId(),
                c.getText(),
                c.getUser().getName(),
                c.getCreatedAt()
        )).collect(Collectors.toList());
    }
}
