<template>
  <div class="viewport">
    <div class="login-container">
      <img
        class="logo"
        src="@/assets/img/logo-blue.png"
        alt="Logo"
        @click="goLogin"
      />

      <div class="input-box">
        <label for="userLoginId" class="label">아이디</label>
        <input
          type="text"
          class="input"
          v-model="user.userLoginId"
          placeholder="아이디를 입력해주세요"
          id="userLoginId"
        />
      </div>

      <div class="input-box">
        <label for="userEmail" class="label">이메일</label>
        <input
          type="text"
          class="input"
          v-model="user.userEmail"
          placeholder="이메일을 입력하여주세요"
          id="userEmail"
        />
        <button class="button" @click="sendAuth">보내기</button>
        <div class="announceBox">
          <h1 v-if="isSend">인증 메일이 발송되었습니다.</h1>
          <h1 v-if="!isSend && isClick">
            <span>인증 메일을 보내는 중.. 발송엔 대략 15초가 소요됩니다</span>
          </h1>
        </div>
      </div>

      <div v-if="isSend" class="input-box">
        <label for="authCode" class="label">인증번호</label>
        <input
          type="text"
          class="input"
          v-model="user.authCode"
          placeholder="인증번호를 입력해주세요"
          id="authCode"
        />
        <button class="button" @click="checkAuthCode">인증하기</button>
      </div>

      <div v-if="isAuth" class="input-box">
        <label for="newPassword" class="label">새 비밀번호</label>
        <input
          type="password"
          class="input"
          v-model="user.newPassword"
          placeholder="새 비밀번호를 입력해주세요"
          id="newPassword"
        />
        <button class="button" @click="saveNewPassword">저장하기</button>
        <div class="announceBox">
          <h1 v-if="isChanged">비밀번호가 성공적으로 변경되었습니다.</h1>
        </div>
      </div>

      <div class="result-box">
        <div v-if="isChanged">
          <button class="button login" @click="goLogin">로그인 하러가기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { reactive, ref } from 'vue';
import { authEmail, authEmailCheck, changePassword } from '@/api/user';

const router = useRouter();

const authNumber = ref('');
const user = reactive({
  userLoginId: '',
  userEmail: '',
  authCode: '',
  newPassword: '',
});

const userId = ref('');
const isAuth = ref(false);
const isSend = ref(false);
const isClick = ref(false);
const isChanged = ref(false);

const sendAuth = async () => {
  try {
    isClick.value = true;
    await authEmail(user.userEmail);
    isSend.value = true;
  } catch (error) {
    console.error('인증 메일 전송 실패:', error);
    alert('인증 메일 전송에 실패했습니다. 다시 시도해주세요.');
    isSend.value = false;
  }
};

const checkAuthCode = async () => {
  try {
    await authEmailCheck(user.userEmail, user.authCode);
    isAuth.value = true;
    alert('인증이 완료되었습니다!');
  } catch (error) {
    alert('올바르지 않은 인증 번호입니다.');
    console.log('error : ', error);
  }
};

const saveNewPassword = async () => {
  try {
    await changePassword(user.userLoginId, user.newPassword);
    alert('비밀번호가 성공적으로 변경되었습니다!');
    isChanged.value = true;
  } catch (error) {
    alert('올바르지 않은 요청입니다.');
  }
};

const goLogin = () => {
  router.push('/login');
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
  width: 70%;
  height: 110px;
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

.result-box {
  width: 100%;
  margin-top: 70px;
}

.result-box a {
  color: #5d8bff;
}

.announceBox {
  margin-top: 20px;
  color: #5d8bff;
}

.button {
  flex: 1;
  width: 27%;
  height: 110px;
  margin-left: 30px;
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

.login {
  margin-top: 3%;
}

span {
  color: 0056b3;
}
</style>
