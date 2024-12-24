<template>
  <div class="viewport">
    <div class="sidebar">
      <img
        src="@/assets/img/logo-blue.png"
        @click="goBack"
        class="logo"
        alt="Logo"
      />
    </div>

    <div class="main">
      <header class="header">
        <span class="header-title">회원가입</span>
      </header>

      <form @submit.prevent="submitForm" class="form">
        <div class="form-group">
          <label for="name" class="label">이름</label>
          <input
            id="name"
            class="input"
            type="text"
            v-model="User.userUsername"
            placeholder="이름을 입력하세요"
            required
          />
        </div>

        <div class="form-group">
          <label for="id" class="label">아이디</label>
          <input
            class="input"
            id="id"
            type="text"
            v-model="User.userLoginId"
            placeholder="ID를 입력하세요"
            @input="idDupCheckAPI"
            required
          />
          <p class="notification" v-if="idDupCheckResult && User.userLoginId">
            이미 사용 중인 아이디입니다.
          </p>
          <p
            class="notification success"
            v-if="idDupCheckOk && User.userLoginId"
          >
            사용 가능한 아이디입니다.
          </p>
        </div>

        <div class="form-group">
          <label for="password" class="label">비밀번호</label>
          <input
            class="input"
            id="password"
            type="password"
            v-model="User.userPassword"
            placeholder="비밀번호를 입력하세요"
            required
          />
          <p class="notification" v-if="passwordError && User.userPassword">
            비밀번호는 영문, 숫자, 특수문자를 포함한 8자 이상이어야 합니다.
          </p>
        </div>

        <div class="form-group">
          <label for="password_check" class="label">비밀번호 확인</label>
          <input
            id="password_check"
            class="input"
            type="password"
            v-model="checkPassword"
            placeholder="비밀번호를 다시 한번 입력하세요"
            required
          />
          <p class="notification" v-if="pwdChecking && checkPassword">
            비밀번호가 일치하지 않습니다.
          </p>
        </div>

        <div class="form-group">
          <label for="nickname" class="label">닉네임</label>
          <input
            class="input"
            id="nickname"
            type="text"
            v-model="User.userNickname"
            placeholder="닉네임을 입력해주세요"
            @input="nicknameDupCheckAPI"
            required
          />
          <p
            class="notification"
            v-if="nicknameDupCheckResult && User.userNickname"
          >
            이미 사용 중인 닉네임입니다.
          </p>
          <p
            class="notification success"
            v-if="nicknameDupCheckOk && User.userNickname"
          >
            사용 가능한 닉네임입니다.
          </p>
        </div>

        <div class="form-group">
          <label for="email" class="label">이메일</label>
          <input
            class="input"
            id="email"
            type="text"
            v-model="User.userEmail"
            placeholder="이메일을 입력해주세요"
            @input="validateEmail"
            @change="emailDupCheckAPI"
            required
          />
          <p class="notification" v-if="emailError && User.userEmail">
            이메일 형식에 맞게 입력해주세요.
          </p>
          <p
            class="notification"
            v-else-if="emailDupCheckResult && !emailError"
          >
            이미 사용 중인 이메일입니다.
          </p>
          <p
            class="notification success"
            v-else-if="emailDupCheckOk && !emailError"
          >
            사용 가능한 이메일입니다.
          </p>
        </div>

        <div class="form-group">
          <label class="label">성별</label>
          <div class="radio-group">
            <label class="radio">
              <input type="radio" v-model="User.userGender" :value="false" />
              여성
            </label>
            <label class="radio">
              <input type="radio" v-model="User.userGender" :value="true" />
              남성
            </label>
          </div>
        </div>

        <div class="form-group">
          <label for="birth" class="label">생년월일</label>
          <input
            class="input"
            id="birth"
            type="text"
            v-model="User.userBirth"
            placeholder="YYYY-MM-DD"
            @input="validateBirthInput"
            required
          />
          <p class="notification" v-if="birthError && User.userBirth">
            올바른 형식(YYYY-MM-DD)으로 입력해주세요.
          </p>
        </div>

        <div class="form-group">
          <button class="submit-button" @click.prevent="submitForm">
            가입하기
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { signUp } from '@/api/user';

const router = useRouter();
const BASEURL = 'http://localhost:8080';

const User = reactive({
  userNickname: '',
  userPassword: '',
  userLoginId: '',
  userUsername: '',
  userEmail: '',
  userGender: null,
  userBirth: '',
});

const checkPassword = ref('');
const idDupCheckResult = ref(false);
const idDupCheckOk = ref(false);
const nicknameDupCheckResult = ref(false);
const nicknameDupCheckOk = ref(false);
const emailDupCheckResult = ref(false);
const emailDupCheckOk = ref(false);
const passwordError = ref(false);
const emailError = ref(false);
const birthError = ref(false);

const goBack = () => {
  router.push('./login');
};

const pwdChecking = computed(() => {
  return (
    User.userPassword !== checkPassword.value && checkPassword.value.length > 0
  );
});

const validatePassword = () => {
  const pattern =
    /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
  passwordError.value = !pattern.test(User.userPassword);
};

const validateEmail = () => {
  const pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  emailError.value = !pattern.test(User.userEmail);
};

const validateBirthInput = () => {
  const pattern = /^\d{4}-\d{2}-\d{2}$/;
  birthError.value = !pattern.test(User.userBirth);
};

const submitForm = async () => {
  try {
    validatePassword();
    validateEmail();
    validateBirthInput();

    if (
      passwordError.value ||
      emailError.value ||
      birthError.value ||
      pwdChecking.value
    ) {
      alert('입력값을 확인해주세요.');
      return;
    }

    const response = await signUp(
      User.userNickname,
      User.userPassword,
      User.userLoginId,
      User.userUsername,
      User.userEmail,
      User.userGender ? 1 : 0,
      User.userBirth
    );

    console.log(response);

    if (response.status === 200) {
      alert('회원가입이 완료되었습니다!');
      router.push('/login');
    }
  } catch (error) {
    console.error(error);
    alert('회원가입에 실패했습니다. 다시 시도해주세요.');
  }
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

.logo {
  width: 600px;
  margin: 0 auto 70px auto;
}

.form-group {
  margin-bottom: 24px;
  text-align: left;
}

.header {
  font-size: 35px;
  font-weight: 500;
  margin-top: 30px;
  margin-bottom: 30px;
}

.radio-group {
  display: flex;
  gap: 20px;
  align-items: center;
}

.radio {
  font-size: 30px;
  display: flex;
  align-items: center;
  gap: 10px;
}

.radio input[type='radio'] {
  width: 25px;
  height: 25px;
  accent-color: #0056b3;
  cursor: pointer;
}

.radio input[type='radio']:hover {
  transform: scale(1.2);
  transition: transform 0.2s ease-in-out;
}

.label {
  display: block;
  font-size: 25px;
  font-weight: 500;
  color: #333;
  margin-bottom: 10px;
}

.notification {
  font-size: 25px;
  margin-top: 15px;
  color: red;
}

.input {
  width: 800px;
  padding: 20px;
  font-size: 30px;
  border: 1px solid #ccc;
  border-radius: 15px;
  box-sizing: border-box;
  transition: border-color 0.2s;
}

.submit-button {
  position: absolute;
  bottom: 330px;
  left: 50%;
  transform: translateX(-50%);
  width: 800px;
  padding: 20px;
  font-size: 30px;
  color: #fff;
  background-color: #5d8bff;
  border: none;
  border-radius: 30px;
  cursor: pointer;
  text-align: center;
  transition: background-color 0.3s ease;
}

.submit-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>
