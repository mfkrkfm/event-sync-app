package com.example.event_sync.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SentimentSummary {
    private int eventId;
    private long totalFeedback;
    private long positive;
    private long neutral;
    private long negative;
}
