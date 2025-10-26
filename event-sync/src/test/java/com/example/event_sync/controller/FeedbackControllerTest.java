package com.example.event_sync.controller;

import com.example.event_sync.model.Event;
import com.example.event_sync.model.Feedback;
import com.example.event_sync.model.Sentiment;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FeedbackControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private int createEventAndGetId() throws Exception {
        Event event = new Event();
        event.setTitle("Team Workshop");
        event.setDescription("Fun activities");

        String eventResponse = mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andReturn().getResponse().getContentAsString();

        Event createdEvent = objectMapper.readValue(eventResponse, Event.class);
        return createdEvent.getId();
    }

    @Test
    public void testAddFeedback() throws Exception {
        int eventId = createEventAndGetId();

        Feedback feedback = new Feedback();
        feedback.setTitle("Great session");
        feedback.setDescription("Loved it");

        mockMvc.perform(post("/events/" + eventId + "/feedback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feedback)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())                     // check that ID was generated
                .andExpect(jsonPath("$.title").value("Great session"))    // check title
                .andExpect(jsonPath("$.description").value("Loved it"));  // check description
    }

    @Test
    public void testGetFeedbackForEvent() throws Exception {
        int eventId = createEventAndGetId();

        Feedback feedback = new Feedback();
        feedback.setTitle("Informative");
        feedback.setDescription("Learned a lot");

        mockMvc.perform(post("/events/" + eventId + "/feedback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feedback)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/events/" + eventId + "/feedback"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title").value("Informative"));
    }

    @Test
    public void testFeedbackSentimentAnalysis() throws Exception {
        int eventId = createEventAndGetId();

        Feedback feedback = new Feedback();
        feedback.setTitle("Loved this event");
        feedback.setDescription("It was amazing and super fun!");

        mockMvc.perform(post("/events/" + eventId + "/feedback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feedback)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/events/" + eventId + "/feedback"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sentiment", is(Sentiment.POSITIVE.name())));
    }

     @Test
    public void testGetEventSummary() throws Exception {
        int eventId = createEventAndGetId();
        Feedback f1 = new Feedback();
        f1.setTitle("Awesome");
        f1.setDescription("Loved every moment!");
        mockMvc.perform(post("/events/" + eventId + "/feedback")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(f1)))
                .andExpect(status().isOk());
        Feedback f2 = new Feedback();
        f2.setTitle("Okay");
        f2.setDescription("It was fine.");
        mockMvc.perform(post("/events/" + eventId + "/feedback")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(f2)))
                .andExpect(status().isOk());
        Feedback f3 = new Feedback();
        f3.setTitle("Bad");
        f3.setDescription("I didn’t enjoy it.");
        mockMvc.perform(post("/events/" + eventId + "/feedback")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(f3)))
                .andExpect(status().isOk());
        mockMvc.perform(get("/events/" + eventId + "/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId").value(eventId))
                .andExpect(jsonPath("$.totalFeedback", greaterThanOrEqualTo(3)))
                .andExpect(jsonPath("$.positive", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.neutral", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.negative", greaterThanOrEqualTo(0)));
    }
}