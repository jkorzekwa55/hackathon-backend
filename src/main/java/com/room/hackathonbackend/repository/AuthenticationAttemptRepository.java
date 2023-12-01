package com.room.hackathonbackend.repository;

import com.room.hackathonbackend.entity.AuthenticationAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthenticationAttemptRepository extends JpaRepository<AuthenticationAttempt, Long> {
    @Query("select e from #{#entityName} e where e.user.id = :user_id order by e.created desc limit 1")
    Optional<AuthenticationAttempt> findLastByUserId(@Param("user_id") Long userId);
}
