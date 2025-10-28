import React, { useEffect, useState } from "react";
import { getEvents, createEvent } from "../api";

const EventList = ({ onSelect }) => {
  const [events, setEvents] = useState([]);
  const [form, setForm] = useState({ title: "", description: "" });

  useEffect(() => {
    loadEvents();
  }, []);

  const loadEvents = async () => {
    const res = await getEvents();
    setEvents(res.data);
  };

  const handleCreate = async (e) => {
    e.preventDefault();
    await createEvent(form);
    setForm({ title: "", description: "" });
    loadEvents();
  };

  return (
    <div className="card">
      <h2>Events</h2>
      <form onSubmit={handleCreate} style={{ marginBottom: "1rem" }}>
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

      <table style={{ width: "100%", borderCollapse: "collapse" }}>
        <thead>
          <tr>
            <th style={{ border: "1px solid #ddd", padding: "8px" }}>ID</th>
            <th style={{ border: "1px solid #ddd", padding: "8px" }}>Title</th>
            <th style={{ border: "1px solid #ddd", padding: "8px" }}>
              Description
            </th>
          </tr>
        </thead>
        <tbody>
          {events.map((ev) => (
            <tr key={ev.id} style={{ border: "1px solid #ddd" }}>
              <td style={{ border: "1px solid #ddd", padding: "8px" }}>
                {ev.id}
              </td>
              <td style={{ border: "1px solid #ddd", padding: "8px" }}>
                <button
                  onClick={() => onSelect(ev.id)}
                  style={{
                    background: "none",
                    border: "none",
                    color: "blue",
                    textDecoration: "underline",
                    cursor: "pointer",
                    padding: "0",
                    fontSize: "inherit",
                    fontFamily: "inherit",
                  }}
                >
                  {ev.title}
                </button>
              </td>
              <td style={{ border: "1px solid #ddd", padding: "8px" }}>
                {ev.description}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default EventList;
