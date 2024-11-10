<template>
  <div class="viewport">
    <Topbar titleText="마이페이지" />
    <TextInput
      class="textname"
      headerInput="이름"
      placeholder="변경할 이름을 입력해 주세요"
      v-model="inputname"
      @input="limitInput"
    />
    <div class="spacer"></div>
    <CtaBar
      class="bottom-bar"
      inputname="이름 등록하기"
      :on="isblack"
      @submit="updateNickname"
    />
  </div>
</template>

<script setup>
import Topbar from '@/components/Topbar.vue';
import TextInput from '@/components/TextInput.vue';
import CtaBar from '@/components/CtaBar.vue';
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const isblack = ref(false);
let inputname = ref('');
const router = useRouter();

const limitInput = () => {
  if (inputname.value.length > 10) {
    inputname.value = inputname.value.slice(0, 10);
  }
  isblack.value = inputname.value.length >= 1;
};

const fetchUserNickname = async () => {
  try {
    const response = await axios.get('/db.json');
    const user = response.data.users[0];
    inputname.value = user.nickname;
    isblack.value = inputname.value.length >= 1;
  } catch (error) {
    console.error('유저 정보 불러오기 오류:', error);
    alert('유저 정보를 불러오는 중 오류가 발생했습니다.');
  }
};

onMounted(() => {
  fetchUserNickname();
});

const updateNickname = async () => {
  if (inputname.value.trim() === '') {
    alert('이름을 입력해주세요.');
    return;
  }

  try {
    const response = await axios.get('/db.json');
    const user = response.data.users[0];

    const updatedUser = { ...user, nickname: inputname.value };

    await axios.put(`http://localhost:3000/users/${user.id}`, updatedUser);

    alert('이름이 성공적으로 등록되었습니다.');
    inputname.value = '';
    isblack.value = false;
    router.push('/hyunsoo');
  } catch (error) {
    console.error('닉네임 변경 오류:', error);
    alert('이름 등록 중 오류가 발생했습니다.');
  }
};
</script>

<style scoped>
.textname {
  white-space: nowrap;
}

.viewport {
  width: 1080px;
  height: 2340px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  margin: 0 auto;
  background-color: #fff;
}

.spacer {
  flex-grow: 1;
}

.bottom-bar {
  margin-top: 30px;
}
</style>
