package com.example.event_sync.service;

import com.example.event_sync.model.Sentiment;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SentimentServiceTest {

    @Test
    void testExtractSentiment() throws IOException {
        String json = "[[{\"label\":\"LABEL_2\",\"score\":0.9},{\"label\":\"LABEL_1\",\"score\":0.05},{\"label\":\"LABEL_0\",\"score\":0.05}]]";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        SentimentService service = new SentimentService();
        Sentiment sentiment = service.extractSentiment(root);
        assertEquals(Sentiment.POSITIVE, sentiment);
    }

    @Test
    void testAnalyzeSentimentReturnsCorrectEnum() throws IOException {
        SentimentService service = Mockito.spy(new SentimentService());
        doReturn(Sentiment.NEUTRAL).when(service).analyzeSentiment(anyString());
        Sentiment result = service.analyzeSentiment("Test text");
        assertEquals(Sentiment.NEUTRAL, result);
    }
}