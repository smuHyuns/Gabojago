<template>
  <div class="viewport">
    <Topbar titleText="마이페이지" />
    <div class="profile-img">
      <img
        class="img"
        @click="showModal = true"
        :src="previewImage || profile.userProfileImg"
        alt="프로필 이미지"
      />
      <div class="profile-btn" @click="changeImg">프로필 이미지 변경하기</div>
      <input
        type="file"
        ref="fileInput"
        hidden
        @change="handleFileChange"
        accept="image/*"
      />
    </div>
    <!-- 모달 창 -->
    <div v-if="showModal" class="modal" @click="showModal = false">
      <div class="modal-content" @click.stop>
        <img
          class="modal-img"
          :src="previewImage || profile.userProfileImg"
          alt="확대된 이미지"
        />
      </div>
    </div>
    <!-- 이름 필드 (읽기 전용) -->
    <TextInput
      class="textname"
      headerInput="이름"
      placeholder="이름은 변경할 수 없습니다"
      v-model="profile.userUsername"
      :readonly="true"
    />
    <div class="spacer"></div>
    <!-- 닉네임 필드 -->
    <TextInput
      class="textname"
      headerInput="닉네임"
      placeholder="변경할 닉네임을 입력해 주세요"
      v-model="profile.userNickname"
    />
    <div class="spacer"></div>
    <!-- 이메일 필드 -->
    <TextInput
      class="textname"
      headerInput="이메일"
      placeholder="변경할 이메일을 입력해 주세요"
      v-model="profile.userEmail"
    />
    <div class="spacer"></div>
    <!-- 비밀번호 필드 (초기값 비어 있음) -->
    <TextInput
      class="textname"
      headerInput="비밀번호"
      placeholder="변경할 비밀번호를 입력해 주세요"
      v-model="profile.userPassword"
    />
    <div class="spacer"></div>
    <CtaBar
      class="bottom-bar"
      inputname="마이페이지 저장하기"
      :on="isSaveEnabled"
      @submit="updateProfile"
    />
  </div>
</template>

<script setup>
import Topbar from '@/components/Topbar.vue';
import TextInput from '@/components/TextInput.vue';
import CtaBar from '@/components/CtaBar.vue';
import { ref, reactive, onMounted, watch } from 'vue';
import { useAuthStore } from '@/stores/auth';
import axios from 'axios';

const authStore = useAuthStore();
const profile = reactive({
  userProfileImg: '',
  userUsername: '', // 이름 필드 (수정 불가)
  userNickname: '',
  userEmail: '',
  userPassword: '', // 비밀번호 필드 초기값 비움
});

const showModal = ref(false); // 모달 창 표시 상태
const isSaveEnabled = ref(false);
const fileInput = ref(null);
const previewImage = ref(''); // 로컬에서 미리보기용 이미지 URL
const isImageChanged = ref(false); // 이미지 변경 여부

// 파일 선택 트리거
const changeImg = () => {
  fileInput.value.click(); // 숨겨진 파일 입력을 트리거
};

// 파일 선택 후 처리
const handleFileChange = (event) => {
  const file = event.target.files[0];
  if (!file) {
    alert('파일을 선택해주세요.');
    return;
  }

  // 로컬에서 미리보기 이미지 설정
  const reader = new FileReader();
  reader.onload = (e) => {
    previewImage.value = e.target.result; // 로컬 이미지 URL 설정
    isImageChanged.value = true; // 이미지 변경 여부 설정
  };
  reader.readAsDataURL(file);
};

// 프로필 업데이트하기
const updateProfile = async () => {
  if (!profile.userNickname.trim() && !profile.userPassword.trim()) {
    alert('닉네임이나 비밀번호 중 하나는 필수 항목입니다.');
    return;
  }

  let imageUrl = profile.userProfileImg; // 기본적으로 기존 URL 사용

  try {
    // 이미지가 변경된 경우 서버 업로드
    if (isImageChanged.value) {
      const file = fileInput.value.files[0];
      if (file) {
        const formData = new FormData();
        formData.append('file', file);

        const uploadResponse = await axios.post(
          'http://localhost:8080/file/upload',
          formData,
          {
            headers: {
              Authorization: `Bearer ${authStore.token}`,
            },
          }
        );
        imageUrl = uploadResponse.data.url; // 새 이미지 URL 설정
      }
    }

    // 프로필 업데이트 API 호출
    const payload = {
      userProfileImg: imageUrl,
      userNickname: profile.userNickname,
      userEmail: profile.userEmail,
      ...(profile.userPassword && { userPassword: profile.userPassword }), // 비밀번호는 입력한 경우에만 포함
    };

    await axios.post('http://localhost:8080/user/profile', payload, {
      headers: { Authorization: `Bearer ${authStore.token}` },
    });

    alert('프로필이 성공적으로 저장되었습니다.');
    profile.userProfileImg = imageUrl; // UI에 업데이트
    profile.userPassword = ''; // 비밀번호 필드 초기화
    previewImage.value = ''; // 로컬 미리보기 초기화
    isImageChanged.value = false; // 이미지 변경 상태 초기화
    fetchProfile(); // 화면 최신화
  } catch (error) {
    console.error('프로필 저장 실패:', error);
    alert('프로필 저장 중 오류가 발생했습니다.');
  }
};

// 모든 필드가 입력되었는지 확인하여 버튼 활성화
watch(
  () => [
    profile.userNickname,
    profile.userEmail,
    profile.userPassword,
    isImageChanged.value,
  ],
  ([nickname, email, password, imageChanged]) => {
    isSaveEnabled.value =
      !!nickname.trim() || !!email.trim() || !!password.trim() || imageChanged;
  }
);

// 프로필 가져오기
const fetchProfile = async () => {
  try {
    const response = await axios.get('http://localhost:8080/user/profile', {
      headers: { Authorization: `Bearer ${authStore.token}` },
    });
    const { userProfileImg, userUsername, userNickname, userEmail } =
      response.data;
    Object.assign(profile, {
      userProfileImg,
      userUsername,
      userNickname,
      userEmail,
    });
  } catch (error) {
    console.error('프로필 불러오기 실패:', error);
    alert('프로필을 불러오는 중 오류가 발생했습니다.');
  }
};

onMounted(() => {
  fetchProfile();
});
</script>

<style scoped>
.textname {
  white-space: nowrap;
}

.viewport {
  width: 1080px;
  height: 2340px;
  overflow: hidden;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 0 auto;
  background-color: #fff;
  position: relative;
  border: 1px solid black;
}

.spacer {
  flex-grow: 1;
}

.bottom-bar {
  margin-top: 30px;
  cursor: pointer;
}

.profile-img {
  text-align: center;
  font-size: 35px;
  cursor: pointer;
}

.profile-btn {
  border-radius: 15px;
  margin-top: 10px;
  margin-bottom: 70px;
  padding: 10px;
  background-color: #3e444e;
  color: snow;
  width: 350px;
  text-align: center;
  cursor: pointer;
}

.img {
  width: 300px;
  height: 300px;
  border-radius: 30px;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  position: relative;
  background: #fff;
  text-align: center;
  padding: 30px;
  border-radius: 10px;
}

.modal-img {
  max-width: 90%;
  max-height: 90%;
  border-radius: 10px;
}
</style>
