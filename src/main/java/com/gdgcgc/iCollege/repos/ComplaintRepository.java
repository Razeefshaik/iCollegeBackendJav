package com.gdgcgc.iCollege.repos;

import com.gdgcgc.iCollege.entities.Complaint;
import com.gdgcgc.iCollege.entities.enums.ComplaintStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    Complaint findByComplaintId(Long complaintId);
    List<Complaint> findByUserId(Long userId);
    List<Complaint> findByStatus(ComplaintStatus status);
    List<Complaint> findByComplaintIdAndUserId(Long complaintId, Long userId);


    List<Complaint> findByUserIdAndStatus(Long userId, ComplaintStatus status);
    List<Complaint> findAllByOrderByCreatedAtDesc();




    @Query(value = "SELECT COUNT(*) FROM complaint_upvotes WHERE complaint_id = :complaintId", nativeQuery = true)
    int countUpvotes(@Param("complaintId") Long complaintId);


    @Query(value = "SELECT COUNT(*) FROM complaint_upvotes WHERE complaint_id = :complaintId AND scholar_id = :userId", nativeQuery = true)
    int hasUserUpvoted(@Param("complaintId") Long complaintId, @Param("userId") String userId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO complaint_upvotes (complaint_id, scholar_id) VALUES (:complaintId, :userId)", nativeQuery = true)
    void addUpvote(@Param("complaintId") Long complaintId, @Param("userId") String userId);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM complaint_upvotes WHERE complaint_id = :complaintId AND scholar_id = :userId", nativeQuery = true)
    void removeUpvote(@Param("complaintId") Long complaintId, @Param("userId") String userId);

}


