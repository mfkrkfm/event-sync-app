import axios from "axios";

const API_BASE = "http://localhost:8081";

export const getEvents = () => axios.get(`${API_BASE}/events`);
export const createEvent = (event) => axios.post(`${API_BASE}/events`, event);

export const addFeedback = (eventId, feedback) =>
  axios.post(`${API_BASE}/events/${eventId}/feedback`, feedback);

export const getFeedback = (eventId) =>
  axios.get(`${API_BASE}/events/${eventId}/feedback`);

export const getSummary = (eventId) =>
  axios.get(`${API_BASE}/events/${eventId}/summary`);
