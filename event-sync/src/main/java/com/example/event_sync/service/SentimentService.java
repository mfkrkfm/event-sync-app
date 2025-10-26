package com.example.event_sync.service;

import com.example.event_sync.model.Sentiment;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class SentimentService {
    @Value("${huggingface.api.url}")
    private String apiUrl;

    @Value("${huggingface.api.key}")
    private String apiKey;

    private final ObjectMapper mapper = new ObjectMapper();

    public Sentiment analyzeSentiment(String text) throws IOException {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            String jsonInputString = "{\"inputs\": \"" + text + "\"}";
            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonInputString.getBytes(StandardCharsets.UTF_8));
            }
            try (InputStream is = connection.getInputStream()) {
                JsonNode root = mapper.readTree(is);
                return extractSentiment(root);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to analyze sentiment: " + e.getMessage(), e);
        }
    }

    Sentiment extractSentiment(JsonNode root) {
        JsonNode scoresArray = root.get(0);
        Sentiment sentiment = null;
        double maxScore = -1;

        for (JsonNode node : scoresArray) {
            double score = node.get("score").asDouble();
            if (score > maxScore) {
                maxScore = score;
                String label = node.get("label").asText();
                sentiment = switch (label) {
                    case "LABEL_0" -> Sentiment.NEGATIVE;
                    case "LABEL_1" -> Sentiment.NEUTRAL;
                    case "LABEL_2" -> Sentiment.POSITIVE;
                    default -> null;
                };
            }
        }
        return sentiment;
    }
}
