package com.gdgcgc.iCollege.repos;



import com.gdgcgc.iCollege.entities.OpinionPoll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OpinionPollRepository extends JpaRepository<OpinionPoll, Long> {
    // Fetch polls that are still running (Expiration Time is in the FUTURE)
    List<OpinionPoll> findByExpiresAtAfter(LocalDateTime now);

    // Fetch polls that have ended (Expiration Time is in the PAST)
    List<OpinionPoll> findByExpiresAtBefore(LocalDateTime now);
}
