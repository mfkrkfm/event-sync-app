import logo from "./logo.svg";
import React, { useState } from "react";
import EventList from "./components/EventList";
import FeedbackForm from "./components/FeedbackForm";
import EventSummary from "./components/EventSummary";
import "./App.css";

function App() {
  const [selectedEventId, setSelectedEventId] = useState(null);
  const [feedbackUpdated, setFeedbackUpdated] = useState(0);
  return (
    <div className="container">
      <h1>EventSync Dashboard</h1>
      <div className="grid">
        <EventList onSelect={setSelectedEventId} />
        {selectedEventId && (
          <>
            <FeedbackForm
              eventId={selectedEventId}
              onFeedbackAdded={() => setFeedbackUpdated((prev) => prev + 1)} // new
            />
            <EventSummary
              eventId={selectedEventId}
              feedbackUpdated={feedbackUpdated} // new
            />
          </>
        )}
      </div>
    </div>
  );
}

export default App;
