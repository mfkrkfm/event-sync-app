# EventSync

EventSync is a Spring Boot backend application that helps teams organize events (workshops, team buildings, small conferences) and collect participant feedback. Feedback is analyzed automatically for sentiment (positive, neutral, negative) using Hugging Face’s NLP API.

---

## Features

- Create and view events
- Submit feedback for specific events
- Automatic sentiment analysis for feedback
- View sentiment breakdown per event
- Simple RESTful API design
- H2 in-memory database for storage (can be replaced with any relational DB)

---

## Tech Stack

- Java 21
- Spring Boot 3.x
- Spring Data JPA
- H2 Database (embedded)

---

## Setup

1. **Clone the repository**

```bash
git clone <repo-url>
cd event-sync