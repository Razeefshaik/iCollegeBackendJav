package com.gdgcgc.iCollege.repos;

import com.gdgcgc.iCollege.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface  UserRepository  extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByEmail(String email);
    Optional<UserInfo> findByScholarId(String scholarId);

    public Boolean existsByEmail(String email);
    public Boolean existsByScholarId(String armyId);

}

