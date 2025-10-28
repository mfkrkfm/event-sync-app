# EventSync

EventSync is a full-stack application for creating events, submitting feedback, and performing sentiment analysis on the feedback. It consists of a Spring Boot backend, a React frontend, and uses the Hugging Face API for sentiment analysis.

---

## Requirements

- Java 21 (for backend)
- Maven (for backend build)
- Node.js 20+ (for frontend)
- npm or yarn (for frontend build)
- Docker & Docker Compose (optional, for containerized deployment)

---

## Setup Instructions

### Backend
1. Add your Hugging Face API key in `.env` in the root folder:
```
HUGGINGFACE_API_KEY=your_api_key_here
```
2. Build and start containers:
```bash
docker-compose up --build
```
- Backend: `http://localhost:8081`
- Frontend: `http://localhost:3000`

---

## Features
- Create events
- Submit feedback for events
- Automatic sentiment analysis (POSITIVE, NEUTRAL, NEGATIVE)
- Event sentiment summary

---

## API Endpoints

### Events
- `GET /events` – List all events
- `POST /events` – Create a new event

### Feedback
- `GET /events/{eventId}/feedback` – Get all feedback for an event
- `POST /events/{eventId}/feedback` – Add feedback to an event
- `GET /events/{eventId}/summary` – Get sentiment summary for the event

---
