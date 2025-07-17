import axios from 'axios';

const BACKEND_URL = process.env.REACT_APP_BACKEND_URL;
const API = `${BACKEND_URL}/api`;

// Create axios instance with default config
const apiClient = axios.create({
  baseURL: API,
  headers: {
    'Content-Type': 'application/json',
  },
});

// API service functions
export const api = {
  // Categories
  categories: {
    getAll: async () => {
      const response = await apiClient.get('/categories');
      return response.data;
    },
    create: async (category) => {
      const response = await apiClient.post('/categories', category);
      return response.data;
    },
  },

  // Habits
  habits: {
    getAll: async () => {
      const response = await apiClient.get('/habits');
      return response.data;
    },
    create: async (habit) => {
      const response = await apiClient.post('/habits', habit);
      return response.data;
    },
    get: async (id) => {
      const response = await apiClient.get(`/habits/${id}`);
      return response.data;
    },
    update: async (id, habit) => {
      const response = await apiClient.put(`/habits/${id}`, habit);
      return response.data;
    },
    delete: async (id) => {
      const response = await apiClient.delete(`/habits/${id}`);
      return response.data;
    },
  },

  // Habit Stacks
  stacks: {
    getAll: async () => {
      const response = await apiClient.get('/stacks');
      return response.data;
    },
    create: async (stack) => {
      const response = await apiClient.post('/stacks', stack);
      return response.data;
    },
    get: async (id) => {
      const response = await apiClient.get(`/stacks/${id}`);
      return response.data;
    },
    update: async (id, stack) => {
      const response = await apiClient.put(`/stacks/${id}`, stack);
      return response.data;
    },
    delete: async (id) => {
      const response = await apiClient.delete(`/stacks/${id}`);
      return response.data;
    },
  },

  // Progress
  progress: {
    getAll: async () => {
      const response = await apiClient.get('/progress');
      return response.data;
    },
    get: async (stackId) => {
      const response = await apiClient.get(`/progress/${stackId}`);
      return response.data;
    },
    update: async (stackId, progress) => {
      const response = await apiClient.put(`/progress/${stackId}`, progress);
      return response.data;
    },
  },

  // Toggle habit completion
  toggleHabit: async (stackId, habitId) => {
    const response = await apiClient.post('/toggle-habit', {
      stack_id: stackId,
      habit_id: habitId,
    });
    return response.data;
  },
};

export default api;