package com.example.event_sync.service;

import com.example.event_sync.model.Event;
import com.example.event_sync.repos.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepo eventRepo;
    public Event createEvent(Event event) {
        return eventRepo.save(event);
    }
    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }
    public void deleteEvent(int id) {
        eventRepo.deleteById(id);
    }
    public Optional<Event> getEventById(int id) {
        return eventRepo.findById(id);
    }
}
