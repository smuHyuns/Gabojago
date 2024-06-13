<template>
  <div class="page-container">
    <div class="container">
      <div class="top">여행자의<br />닉네임을 적어주세요!</div>

      <input
        type="text"
        placeholder="10자 이내로 입력해주세요"
        class="middle"
        v-model="inputname"
        @input="limitInput"
      />
    </div>

    <CtaBar
      class="down"
      inputname="여행자 닉네임 변경"
      :on="isblack"
      @submit="updateNickname"
    />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import CtaBar from '@/components/CtaBar.vue';

const isblack = ref(false);
let inputname = ref('');
const router = useRouter();

const limitInput = (event) => {
  if (event.target.value.length > 10) {
    inputname.value = event.target.value.slice(0, 10);
  }
  isblack.value = inputname.value.length >= 1;
  console.log(isblack.value);
};

const updateNickname = async () => {
  if (inputname.value.trim() === '') {
    alert('닉네임을 입력해주세요.');
    return;
  }

  try {
    const response = await axios.get('/db.json');
    const user = response.data.users[0];

    const updatedUser = { ...user, nickname: inputname.value };

    await axios.put(`http://localhost:3000/users/${user.id}`, updatedUser);

    alert('닉네임이 성공적으로 변경되었습니다.');
    inputname.value = '';
    isblack.value = false;
    router.push('/hyunsoo');
  } catch (error) {
    console.error('닉네임 변경 오류:', error);
    alert('닉네임 변경 중 오류가 발생했습니다.');
  }
};
</script>

<style scoped>
.container {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  padding-top: 400px;
}
.top {
  width: 100%;
  font-style: normal;
  font-weight: 700;
  font-size: 90px;
  line-height: 1.4;
  text-align: left;
  width: 965px;
  margin: 0 auto;
  color: #353b43;
}
.middle {
  width: 910px;
  height: 152px;
  padding: 12px 16px;
  padding-left: 50px; /* 왼쪽 마진 */
  margin-top: 20px;
  background: #f5f6f7;
  border-radius: 46px;
  font-style: normal;
  font-weight: 500;
  font-size: 60px;
  line-height: 1.3;
  color: #353b43;
  border: none;
  outline: none; /* 클릭 시 파란 테두리 제거 */
}
.middle::placeholder {
  color: #caced4;
}
.middle:focus::placeholder {
  color: transparent; /* 포커스 시 placeholder 숨김 */
}

.page-container {
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

.down {
  position: absolute;
  bottom: 133px;
  left: 50%;
  transform: translateX(-50%);
}
</style>
