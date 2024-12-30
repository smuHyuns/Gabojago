// stores/auth.js
import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || null,
  }),
  actions: {
    setToken(token) {
      this.token = token;
      localStorage.setItem('token', token);
    },
    clearAuth() {
      this.token = null;
      this.user = null;
      localStorage.removeItem('token');
      localStorage.removeItem('user');
    },
  },
});
