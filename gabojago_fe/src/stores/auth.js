// stores/auth.js
import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || null, // 새로고침에도 유지
  }),
  actions: {
    setToken(token) {
      this.token = token;
      localStorage.setItem('token', token); // 로컬스토리지에 저장
    },
    clearAuth() {
      this.token = null;
      this.user = null;
      localStorage.removeItem('token'); // 로컬스토리지에서 제거
      localStorage.removeItem('user'); // 로컬스토리지에서 제거
    },
  },
});
