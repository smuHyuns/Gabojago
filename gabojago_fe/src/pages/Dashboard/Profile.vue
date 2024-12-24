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

    <div v-if="showModal" class="modal" @click="showModal = false">
      <div class="modal-content" @click.stop>
        <img
          class="modal-img"
          :src="previewImage || profile.userProfileImg"
          alt="확대된 이미지"
        />
      </div>
    </div>

    <TextInput
      class="textname"
      headerInput="이름"
      placeholder="이름은 변경할 수 없습니다"
      v-model="profile.userUsername"
      :readonly="true"
    />
    <div class="spacer"></div>

    <TextInput
      class="textname"
      headerInput="닉네임"
      placeholder="변경할 닉네임을 입력해 주세요"
      v-model="profile.userNickname"
    />
    <div class="spacer"></div>

    <TextInput
      class="textname"
      headerInput="이메일"
      placeholder="변경할 이메일을 입력해 주세요"
      v-model="profile.userEmail"
    />
    <div class="spacer"></div>

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
import Topbar from '@/components/used/Topbar.vue';
import TextInput from '@/components/used/TextInput.vue';
import CtaBar from '@/components/used/CtaBar.vue';
import { ref, reactive, onMounted, watch } from 'vue';
import { useAuthStore } from '@/stores/auth';
import axios from 'axios';
import { upload } from '@/api/file';
import { getUserProfile, updateNewProfile } from '@/api/user';

const authStore = useAuthStore();
const profile = reactive({
  userProfileImg: '',
  userUsername: '',
  userNickname: '',
  userEmail: '',
  userPassword: '',
});

const showModal = ref(false);
const isSaveEnabled = ref(false);
const fileInput = ref(null);
const previewImage = ref('');
const isImageChanged = ref(false);

const changeImg = () => {
  fileInput.value.click();
};

const handleFileChange = (event) => {
  const file = event.target.files[0];
  if (!file) {
    alert('파일을 선택해주세요.');
    return;
  }

  const reader = new FileReader();
  reader.onload = (e) => {
    previewImage.value = e.target.result;
    isImageChanged.value = true;
  };
  reader.readAsDataURL(file);
};

const updateProfile = async () => {
  if (!profile.userNickname.trim() && !profile.userPassword.trim()) {
    alert('닉네임이나 비밀번호 중 하나는 필수 항목입니다.');
    return;
  }

  let imageUrl = profile.userProfileImg;

  try {
    if (isImageChanged.value) {
      const file = fileInput.value.files[0];
      if (file) {
        const formData = new FormData();
        formData.append('file', file);

        const uploadResponse = await upload(formData);
        imageUrl = uploadResponse.data.url;
      }
    }

    const payload = {
      userProfileImg: imageUrl,
      userNickname: profile.userNickname,
      userEmail: profile.userEmail,
      ...(profile.userPassword && { userPassword: profile.userPassword }),
    };

    updateNewProfile(payload);

    alert('프로필이 성공적으로 저장되었습니다.');
    profile.userProfileImg = imageUrl;
    profile.userPassword = '';
    previewImage.value = '';
    isImageChanged.value = false;
    fetchProfile();
  } catch (error) {
    console.error('프로필 저장 실패:', error);
    alert('프로필 저장 중 오류가 발생했습니다.');
  }
};

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

const fetchProfile = async () => {
  try {
    const response = await getUserProfile();
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
