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
import { useRouter } from 'vue-router';
import { login } from '@/services/AuthService';

const router = useRouter();
const loginUser = {
  userLoginId: '',
  userPassword: '',
};

const handleLogin = async () => {
  try {
    await login(loginUser);
  } catch (error) {
    console.error('로그인 처리 중 오류 발생:', error);
  }
};

const GoSignUp = () => {
  router.push('/sign-up');
};

const GoFindId = () => {
  router.push('/find-id');
};

const GoFindPwd = () => {
  router.push('/find-pw');
};
</script>

<style scoped>
.viewport {
  width: 1080px;
  height: 2340px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 0 auto;
  background-color: #fff;
  position: relative;
}

.login-container {
  width: 100%;
  max-width: 1000px;
  background-color: #ffffff;
  border-radius: 12px;
  text-align: center;
}

.logo {
  width: 600px;
  margin: 0 auto 70px auto;
}

.input-box {
  margin-bottom: 24px;
  text-align: left;
}

.label {
  display: block;
  font-size: 35px;
  font-weight: 500;
  color: #333;
  margin-bottom: 10px;
}

.input {
  width: 100%;
  height: 130px;
  padding: 12px;
  font-size: 40px;
  border: 1px solid #ccc;
  border-radius: 30px;
  box-sizing: border-box;
  transition: border-color 0.2s;
}

.input:focus {
  /* border-color: ;  */
  outline: none;
}

.button-group {
  display: flex;
  gap: 12px;
  height: 100px;
  margin-top: 20px;
}

.button {
  flex: 1;
  padding: 12px 20px;
  font-size: 30px;
  font-weight: bold;
  color: #fff;
  background-color: #5d8bff;
  border: none;
  border-radius: 30px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.button:disabled {
  background-color: #c0c0c0;
  cursor: not-allowed;
}

.button:hover:not(:disabled) {
  background-color: #0056b3;
}

.link-group {
  margin-top: 24px;
  display: flex;
  justify-content: space-between;
}

.link-button {
  font-size: 30px;
  color: #007bff;
  background: none;
  border: none;
  text-decoration: underline;
  cursor: pointer;
  padding: 10px 15px;
}

.link-button:hover {
  color: #0056b3;
}
</style>
