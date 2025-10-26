package com.example.event_sync.service;

import com.example.event_sync.model.Event;
import com.example.event_sync.model.Feedback;
import com.example.event_sync.model.Sentiment;
import com.example.event_sync.repos.EventRepo;
import com.example.event_sync.repos.FeedbackRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeedbackServiceTest {

    private FeedbackService feedbackService;
    private EventRepo eventRepo;
    private FeedbackRepo feedbackRepo;
    private SentimentService sentimentService;

    @BeforeEach
    void setUp() {
        eventRepo = mock(EventRepo.class);
        feedbackRepo = mock(FeedbackRepo.class);
        sentimentService = mock(SentimentService.class);
        feedbackService = new FeedbackService(eventRepo, feedbackRepo, sentimentService);
    }

    @Test
    void testAddFeedbackSuccess() throws IOException {
        Event event = new Event();
        event.setId(1);
        Feedback feedback = new Feedback();
        feedback.setTitle("Great!");
        feedback.setDescription("Loved it");
        when(eventRepo.findById(1)).thenReturn(Optional.of(event));
        when(sentimentService.analyzeSentiment(feedback.getDescription())).thenReturn(Sentiment.POSITIVE);
        when(feedbackRepo.save(feedback)).thenReturn(feedback);
        Feedback result = feedbackService.addFeedback(1, feedback);
        assertNotNull(result);
        assertEquals(Sentiment.POSITIVE, result.getSentiment());
        verify(feedbackRepo, times(1)).save(feedback);
    }

    @Test
    void testAddFeedbackEventNotFound() {
        Feedback feedback = new Feedback();
        when(eventRepo.findById(1)).thenReturn(Optional.empty());
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> feedbackService.addFeedback(1, feedback));
        assertEquals("Event not found, id: 1", ex.getMessage());
    }
}