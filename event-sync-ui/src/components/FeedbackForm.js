import React, { useState, useEffect } from "react";
import { addFeedback, getFeedback } from "../api";

const FeedbackForm = ({ eventId, onFeedbackAdded }) => {
  const [feedbackList, setFeedbackList] = useState([]); // only declare once
  const [form, setForm] = useState({ title: "", description: "" }); // only declare once

  useEffect(() => {
    if (eventId) loadFeedback();
  }, [eventId]);

  const loadFeedback = async () => {
    const res = await getFeedback(eventId);
    setFeedbackList(res.data);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    await addFeedback(eventId, form);
    setForm({ title: "", description: "" });
    loadFeedback();
    if (onFeedbackAdded) onFeedbackAdded(); // notify parent to refresh summary
  };

  return (
    <div className="card">
      <h2>Feedback</h2>
      <form onSubmit={handleSubmit} style={{ marginBottom: "1rem" }}>
        <input
          placeholder="Title"
          value={form.title}
          onChange={(e) => setForm({ ...form, title: e.target.value })}
          style={{
            width: "calc(100% - 1rem)",
            margin: "0.5rem 0",
            padding: "0.5rem",
            boxSizing: "border-box",
          }}
        />
        <textarea
          placeholder="Description"
          value={form.description}
          onChange={(e) => setForm({ ...form, description: e.target.value })}
          style={{
            width: "calc(100% - 1rem)",
            height: "60px",
            margin: "0.5rem 0",
            padding: "0.5rem",
            resize: "none", // disables resizing
            boxSizing: "border-box",
          }}
        />
        <button type="submit">Add Event</button>
      </form>

      <ul>
        {feedbackList.map((f) => (
          <li key={f.id}>
            <b>{f.title}</b>: {f.description} ({f.sentiment})
          </li>
        ))}
      </ul>
    </div>
  );
};

export default FeedbackForm;
