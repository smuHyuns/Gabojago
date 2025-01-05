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
        <label for="name" class="label">이름</label>
        <input
          type="text"
          class="input"
          v-model="user.username"
          placeholder="이름을 입력해주세요"
          id="username"
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
        </div>
      </div>

      <div v-if="isSend" class="input-box">
        <label for="emailCode" class="label">인증번호</label>
        <input
          type="text"
          class="input"
          v-model="user.emailCode"
          placeholder="인증번호를 입력해주세요"
          id="emailCode"
        />
        <button class="button" @click="checkAuth">인증하기</button>
      </div>

      <div class="result-box">
        <h1 v-if="isAuth">
          고객님의 아이디는
          <a>{{ userId }}</a>
          입니다
        </h1>
        <h1 v-if="!isAuth && isSend">
          인증을 완료하면 아이디가 이곳에 표시됩니다
        </h1>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { reactive, ref } from 'vue';
import { authEmail, findId } from '@/api/user';

const router = useRouter();

const authNumber = ref('');
const user = reactive({
  userEmail: '',
  emailCode: '',
});
const username = ref('');
const userId = ref('');
const isAuth = ref(false);
const isSend = ref(false);

const sendAuth = async () => {
  try {
    const response = await authEmail(user.userEmail);
    authNumber.value = response.data.authenticateNumber;
    console.log('authNumber :', authNumber.value);
    isSend.value = true;
  } catch (error) {
    console.error('인증 메일 전송 실패:', error);
    alert('인증 메일 전송에 실패했습니다. 다시 시도해주세요.');
    isSend.value = false;
  }
};

const checkAuth = async () => {
  if (authNumber.value === user.emailCode) {
    isAuth.value = true;
    const response = await findId(user.username, user.userEmail);
    userId.value = response;
    console.log(userId.value);
    alert('인증이 완료되었습니다!');
  } else {
    alert('인증번호가 일치하지 않습니다!');
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
</style>
