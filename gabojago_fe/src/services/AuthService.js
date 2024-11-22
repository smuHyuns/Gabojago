// services/AuthService.js
import axios from 'axios';
import router from '../router'; // Vue Router 사용
import { useAuthStore } from '../stores/auth';

const BASEURL = 'http://localhost:8080';

// 로그인
export const login = async (credentials) => {
  try {
    const response = await axios.post(`${BASEURL}/user/login`, {
      userLoginId: credentials.userLoginId,
      userPassword: credentials.userPassword,
    });

    if (response.status === 200) {
      const { token, user } = response.data;

      // Pinia Store에 토큰과 사용자 정보 저장
      const authStore = useAuthStore();
      authStore.setToken(token);

      // 콘솔 로그로 저장된 값 출력
      console.log('로그인 성공! 저장된 토큰:', authStore.token);

      alert('로그인 성공!');
      router.push('/dashboard'); // 로그인 성공 시 대시보드로 이동
    }
  } catch (error) {
    console.error('로그인 실패:', error);
    alert('로그인에 실패했습니다. 다시 시도해주세요.');
  }
};

// 로그아웃
export const logout = () => {
  try {
    const authStore = useAuthStore(); // Pinia Store 접근
    authStore.clearAuth(); // 인증 정보 제거

    console.log('로그아웃 성공!');

    alert('로그아웃 되었습니다.');
    router.push('/login'); // 로그아웃 후 로그인 페이지로 이동
  } catch (error) {
    console.error('로그아웃 실패:', error);
    alert('로그아웃에 실패했습니다.');
  }
};
