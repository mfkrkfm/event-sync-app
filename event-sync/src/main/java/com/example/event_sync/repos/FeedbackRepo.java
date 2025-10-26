package com.example.event_sync.repos;

import com.example.event_sync.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepo extends JpaRepository<Feedback, Integer> {
    List<Feedback> findByEventId(int eventId);
}
