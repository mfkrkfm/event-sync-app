package com.example.event_sync.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString(exclude = "event")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String description;
    @ManyToOne
    @JsonBackReference
    private Event event;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private Sentiment sentiment;

    public Feedback(String title, String description, Event event) {
        this.title = title;
        this.description = description;
        this.event = event;
    }

    public Feedback() {
        this.createdAt = LocalDateTime.now();
    }
}
