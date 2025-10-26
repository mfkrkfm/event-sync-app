package com.example.event_sync.controllers;

import com.example.event_sync.model.Feedback;
import com.example.event_sync.model.SentimentSummary;
import com.example.event_sync.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/events")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/{eventId}/feedback")
    public ResponseEntity<Feedback> addFeedback(
            @PathVariable int eventId,
            @RequestBody Feedback feedback) throws IOException {
        return ResponseEntity.ok(feedbackService.addFeedback(eventId, feedback));
    }

    @GetMapping("/{eventId}/feedback")
    public ResponseEntity<List<Feedback>> getAllFeedback(@PathVariable int eventId) {
        return ResponseEntity.ok(feedbackService.getFeedbackForEvent(eventId));
    }

     @DeleteMapping("/{eventId}/feedback/{feedbackId}")
    public ResponseEntity<String> deleteFeedback(
            @PathVariable int eventId,
            @PathVariable int feedbackId) {
        Feedback feedback = feedbackService.getFeedbackById(feedbackId);
        if (feedback.getEvent().getId() != eventId) {
            return ResponseEntity.badRequest().body("Feedback does not belong to this event");
        }
        feedbackService.deleteFeedback(feedbackId);
        return ResponseEntity.ok("Feedback deleted successfully");
    }

    @GetMapping("/{eventId}/summary")
    public ResponseEntity<SentimentSummary> getEventSummary(@PathVariable int eventId) {
        SentimentSummary summary = feedbackService.getSentimentSummary(eventId);
        return ResponseEntity.ok(summary);
    }
}
