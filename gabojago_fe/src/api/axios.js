import axios from 'axios';
import { useAuthStore } from '@/stores/auth';
import { API_BASE_URL } from '@/config/config';

const instance = axios.create({
  baseURL: `${API_BASE_URL}`, // 백엔드 URL
  timeout: 10000, // 10초 타임아웃
});

instance.interceptors.request.use((config) => {
  const authStore = useAuthStore();
  const token = authStore.token;
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default instance;
