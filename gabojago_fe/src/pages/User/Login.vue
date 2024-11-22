<template>
  <div class="viewport">
    <div class="login-container">
      <img class="logo" src="@/assets/img/logo-blue.png" alt="Logo" />

      <div class="input-box">
        <label for="id" class="label">아이디</label>
        <input
          type="text"
          class="input"
          v-model="loginUser.userLoginId"
          placeholder="아이디를 입력하세요"
          id="id"
        />
      </div>

      <div class="input-box">
        <label for="password" class="label">비밀번호</label>
        <input
          type="password"
          class="input"
          v-model="loginUser.userPassword"
          placeholder="비밀번호를 입력하세요"
          id="password"
        />
      </div>

      <div class="button-group">
        <button class="button" @click="GoSignUp">회원가입</button>
        <button class="button" @click="handleLogin">로그인</button>
      </div>

      <div class="link-group">
        <button class="link-button" @click="GoFindId">아이디 찾기</button>
        <button class="link-button" @click="GoFindPwd">비밀번호 찾기</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'; // useRouter 훅 가져오기
import { login } from '@/services/AuthService'; // AuthService의 login 함수 가져오기

const router = useRouter(); // useRouter 훅 사용
const loginUser = {
  userLoginId: '',
  userPassword: '',
};

// 로그인 버튼 클릭 핸들러
const handleLogin = async () => {
  try {
    await login(loginUser); // AuthService의 login 함수 호출
  } catch (error) {
    console.error('로그인 처리 중 오류 발생:', error);
  }
};

// 회원가입 페이지로 이동
const GoSignUp = () => {
  router.push('/sign-up');
};

// 아이디 찾기 페이지로 이동
const GoFindId = () => {
  router.push('/find-id');
};

// 비밀번호 찾기 페이지로 이동
const GoFindPwd = () => {
  router.push('/find-password');
};
</script>

<style scoped>
/* 기존 viewport 스타일 유지 */
.viewport {
  width: 1080px;
  height: 2340px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center; /* 요소를 중앙에 배치 */
  margin: 0 auto;
  background-color: #fff;
  position: relative;
  border: 1px solid black;
}

/* 로그인 컨테이너 */
.login-container {
  width: 100%;
  max-width: 1000px;
  background-color: #ffffff;
  /* box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);  */
  border-radius: 12px; /* 둥근 모서리 확대 */
  text-align: center;
}

/* 로고 */
.logo {
  width: 600px; /* 로고 크기 확대 */
  margin: 0 auto 70px auto; /* 여백 추가 */
}

/* 입력 박스 */
.input-box {
  margin-bottom: 24px; /* 입력 박스 사이 간격 */
  text-align: left;
}

.label {
  display: block;
  font-size: 35px; /* 글자 크기 확대 */
  font-weight: 500; /* 굵게 */
  color: #333;
  margin-bottom: 10px; /* 여백 추가 */
}

.input {
  width: 100%;
  height: 130px;
  padding: 12px;
  font-size: 40px;
  border: 1px solid #ccc;
  border-radius: 30px;
  box-sizing: border-box; /* 패딩 포함한 박스 크기 계산 */
  transition: border-color 0.2s;
}

.input:focus {
  border-color: ; /* 포커스 시 강조 */
  outline: none;
}

/* 버튼 그룹 */
.button-group {
  display: flex;
  gap: 12px; /* 버튼 사이 간격 추가 */
  height: 100px;
  margin-top: 20px;
}

.button {
  flex: 1;
  padding: 12px 20px; /* 버튼 크기 확대 */
  font-size: 30px; /* 버튼 텍스트 크기 확대 */
  font-weight: bold;
  color: #fff;
  background-color: #5d8bff; /* 기본 파란색 */
  border: none;
  border-radius: 30px; /* 모서리를 둥글게 */
  cursor: pointer;
  transition: background-color 0.3s;
}

.button:disabled {
  background-color: #c0c0c0; /* 비활성화 상태 */
  cursor: not-allowed;
}

.button:hover:not(:disabled) {
  background-color: #0056b3; /* 호버 시 어두운 파란색 */
}

/* 링크 그룹 */
.link-group {
  margin-top: 24px; /* 링크 그룹 상단 간격 */
  display: flex;
  justify-content: space-between; /* 링크 간 간격 추가 */
}

.link-button {
  font-size: 30px;
  color: #007bff;
  background: none;
  border: none;
  text-decoration: underline;
  cursor: pointer;
  padding: 10px 15px; /* 버튼 형태로 구현 */
}

.link-button:hover {
  color: #0056b3; /* 호버 색상 */
}
</style>
