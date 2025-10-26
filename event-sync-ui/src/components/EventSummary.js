import React, { useEffect, useState } from "react";
import { getSummary } from "../api";

const EventSummary = ({ eventId, feedbackUpdated }) => {
  const [summary, setSummary] = useState(null);

  useEffect(() => {
    if (!eventId) return;

    const loadSummary = async () => {
      const res = await getSummary(eventId);
      setSummary(res.data);
    };

    loadSummary();
  }, [eventId, feedbackUpdated]); // <-- add feedbackUpdated

  if (!summary) return null;

  return (
    <div className="card">
      <h2>Event Summary</h2>
      <p>Total Feedback: {summary.totalFeedback}</p>
      <p>Positive: {summary.positive}</p>
      <p>Neutral: {summary.neutral}</p>
      <p>Negative: {summary.negative}</p>
    </div>
  );
};

export default EventSummary;
