package com.example.event_sync.service;

import com.example.event_sync.model.Event;
import com.example.event_sync.model.Feedback;
import com.example.event_sync.model.Sentiment;
import com.example.event_sync.model.SentimentSummary;
import com.example.event_sync.repos.EventRepo;
import com.example.event_sync.repos.FeedbackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {
    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private FeedbackRepo feedbackRepo;
    @Autowired
    private SentimentService sentimentService;

    public FeedbackService(EventRepo eventRepo, FeedbackRepo feedbackRepo, SentimentService sentimentService) {
        this.eventRepo = eventRepo;
        this.feedbackRepo = feedbackRepo;
        this.sentimentService = sentimentService;
    }

    public Feedback addFeedback(int eventId, Feedback feedback) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found, id: " + eventId));
        feedback.setEvent(event);
        try {
            feedback.setSentiment(sentimentService.analyzeSentiment(feedback.getDescription()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to analyze sentiment", e);
        }
        return feedbackRepo.save(feedback);
    }

    public List<Feedback> getFeedbackForEvent(int eventId) {
        Optional<Event> optionalEvent = eventRepo.findById(eventId);
        if (optionalEvent.isEmpty()) {
            throw new IllegalArgumentException("Event not found, id: " + eventId);
        }
        Event event = optionalEvent.get();
        return event.getFeedbackList();
    }

    public Feedback getFeedbackById(int feedbackId) {
        return feedbackRepo.findById(feedbackId)
                .orElseThrow(() -> new IllegalArgumentException("Feedback not found"));
    }

     public void deleteFeedback(int feedbackId) {
        Optional<Feedback> optionalFeedback = feedbackRepo.findById(feedbackId);
        if (optionalFeedback.isEmpty()) {
            throw new IllegalArgumentException("Feedback not found, id: " + feedbackId);
        }
        feedbackRepo.delete(optionalFeedback.get());
    }

    public SentimentSummary getSentimentSummary(int eventId) {
        List<Feedback> feedbackList = feedbackRepo.findByEventId(eventId);
        if (feedbackList.isEmpty()) {
            return new SentimentSummary(eventId, 0, 0, 0, 0);
        }
        long positive = feedbackList.stream().filter(f -> f.getSentiment() == Sentiment.POSITIVE).count();
        long neutral = feedbackList.stream().filter(f -> f.getSentiment() == Sentiment.NEUTRAL).count();
        long negative = feedbackList.stream().filter(f -> f.getSentiment() == Sentiment.NEGATIVE).count();
        return new SentimentSummary(eventId, feedbackList.size(), positive, neutral, negative);
    }

}
