package com.room.hackathonbackend.repository;

import com.room.hackathonbackend.entity.EventResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventResponseRepository extends JpaRepository<EventResponse, Long> {
}
