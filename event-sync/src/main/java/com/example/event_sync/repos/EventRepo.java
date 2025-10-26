package com.example.event_sync.repos;

import com.example.event_sync.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<Event, Integer> {
}
