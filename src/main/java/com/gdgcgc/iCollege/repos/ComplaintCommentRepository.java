package com.gdgcgc.iCollege.repos;


import com.gdgcgc.iCollege.entities.Complaint;
import com.gdgcgc.iCollege.entities.ComplaintComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintCommentRepository extends JpaRepository<ComplaintComment, Long> {

    List<ComplaintComment> findByComplaintComplaintIdOrderByCreatedAtDesc(Long complaintId);
}
