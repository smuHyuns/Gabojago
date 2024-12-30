// services/AuthService.js
import axios from 'axios';
import router from '../router';
import { useAuthStore } from '../stores/auth';

const BASEURL = 'http://localhost:8080';

export const login = async (credentials) => {
  try {
    const response = await axios.post(`${BASEURL}/user/login`, {
      userLoginId: credentials.userLoginId,
      userPassword: credentials.userPassword,
    });

    if (response.status === 200) {
      const { token, user } = response.data;

      const authStore = useAuthStore();
      authStore.setToken(token);
      alert('로그인 성공!');
      router.push('/dashboard');
    }
  } catch (error) {
    console.error('로그인 실패:', error);
    alert('로그인에 실패했습니다. 다시 시도해주세요.');
  }
};

// 로그아웃
export const logout = () => {
  try {
    const authStore = useAuthStore();
    authStore.clearAuth();

    alert('로그아웃 되었습니다.');
    router.push('/login');
  } catch (error) {
    console.error('로그아웃 실패:', error);
    alert('로그아웃에 실패했습니다.');
  }
};
