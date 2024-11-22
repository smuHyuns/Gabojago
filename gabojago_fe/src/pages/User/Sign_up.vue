<template>
  <div class="viewport">
    <!-- 왼쪽 파란 배경 (웹에서만 적용) -->
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
        <!-- 이름 -->
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
        <!-- 아이디 -->
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

        <!-- 비밀번호 -->
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

        <!-- 비밀번호 확인 -->
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

        <!-- 닉네임 -->
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

        <!-- 이메일 -->
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

        <!-- 성별 -->
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

        <!-- 생년월일 -->
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

        <!-- 가입하기 버튼 -->
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

const router = useRouter();
const BASEURL = 'http://localhost:8080';

// User 객체를 RequestSignUpDto에 맞게 정의
const User = reactive({
  userNickname: '',
  userPassword: '',
  userLoginId: '',
  userUsername: '',
  userEmail: '',
  userGender: null, // true = 여성, false = 남성
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

// 비밀번호 일치 확인
const pwdChecking = computed(() => {
  return (
    User.userPassword !== checkPassword.value && checkPassword.value.length > 0
  );
});

// 비밀번호 검증
const validatePassword = () => {
  const pattern =
    /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
  passwordError.value = !pattern.test(User.userPassword);
};

// 이메일 검증
const validateEmail = () => {
  const pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  emailError.value = !pattern.test(User.userEmail);
};

// 생년월일 검증
const validateBirthInput = () => {
  const pattern = /^\d{4}-\d{2}-\d{2}$/;
  birthError.value = !pattern.test(User.userBirth);
};

// 회원가입 요청
const submitForm = async () => {
  try {
    // 입력값 검증
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

    // 서버로 데이터 전송
    const response = await axios.post(`${BASEURL}/user/sign-up`, {
      user_nickname: User.userNickname,
      user_password: User.userPassword,
      user_login_id: User.userLoginId,
      user_username: User.userUsername,
      user_email: User.userEmail,
      user_gender: User.userGender ? 1 : 0,
      user_birth: User.userBirth,
    });

    console.log(response);

    if (response.status === 200) {
      alert('회원가입이 완료되었습니다!');
      router.push('/login'); // 성공 시 로그인 페이지로 이동
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
  border: 1px solid black;
}

/* 로고 */
.logo {
  width: 600px; /* 로고 크기 확대 */
  margin: 0 auto 70px auto; /* 여백 추가 */
}

.form-group {
  margin-bottom: 24px; /* 입력 박스 사이 간격 */
  text-align: left;
}

.header {
  font-size: 35px; /* 글자 크기 확대 */
  font-weight: 500; /* 굵게 */
  margin-top: 30px;
  margin-bottom: 30px;
}

.radio-group {
  display: flex;
  gap: 20px; /* 버튼 사이 간격 */
  align-items: center; /* 수직 정렬 */
}

.radio {
  font-size: 30px; /* 텍스트 크기 확대 */
  display: flex;
  align-items: center; /* 텍스트와 버튼 수직 정렬 */
  gap: 10px; /* 버튼과 텍스트 사이 간격 */
}

.radio input[type='radio'] {
  width: 25px; /* 버튼 크기 */
  height: 25px; /* 버튼 크기 */
  accent-color: #0056b3; /* 버튼 색상 */
  cursor: pointer; /* 클릭 가능한 느낌 제공 */
}

.radio input[type='radio']:hover {
  transform: scale(1.2); /* 마우스 올릴 때 버튼 확대 */
  transition: transform 0.2s ease-in-out;
}

.label {
  display: block;
  font-size: 25px; /* 글자 크기 확대 */
  font-weight: 500; /* 굵게 */
  color: #333;
  margin-bottom: 10px; /* 여백 추가 */
}

.notification {
  font-size: 25px; /* 글자 크기 확대 */
  margin-top: 15px;
  color: red;
}

.input {
  width: 800px;
  padding: 20px;
  font-size: 30px;
  border: 1px solid #ccc;
  border-radius: 15px;
  box-sizing: border-box; /* 패딩 포함한 박스 크기 계산 */
  transition: border-color 0.2s;
}

.submit-button {
  position: absolute; /* 절대 위치 지정 */
  bottom: 330px; /* viewport 하단에서 150px 위로 위치 */
  left: 50%; /* 수평 중앙 정렬 */
  transform: translateX(-50%); /* 수평 중앙 정렬 보정 */
  width: 800px; /* 입력 필드와 동일한 너비 */
  padding: 20px; /* 버튼 패딩 */
  font-size: 30px; /* 버튼 텍스트 크기 */
  color: #fff;
  background-color: #5d8bff;
  border: none;
  border-radius: 30px; /* 둥근 모서리 */
  cursor: pointer;
  text-align: center;
  transition: background-color 0.3s ease; /* 배경색 전환 효과 */
}

.submit-button:disabled {
  background-color: #ccc; /* 비활성화 상태 */
  cursor: not-allowed;
}
</style>
