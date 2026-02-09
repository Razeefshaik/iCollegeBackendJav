package com.gdgcgc.iCollege.repos;

import org.springframework.data.jpa.repository.JpaRepository;



import com.gdgcgc.iCollege.entities.PollVote;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PollVoteRepository extends JpaRepository<PollVote, Long> {
    boolean existsByPollIdAndScholarId(Long pollId, String scholarId);

    Optional<PollVote> findByPollIdAndScholarId(Long pollId, String scholarId);
}