<template>
  <div class="page-container">
    <div class="container">
      <div class="top">여행자의<br />닉네임을 적어주세요!</div>

      <input
        type="text"
        placeholder="10자 이내로 입력해주세요."
        class="middle"
        v-model="inputname"
        @input="limitInput"
      />
    </div>

    <CtaBar class="down" inputname="여행자 등록하기" :on="isblack" />
  </div>
</template>

<script setup>
import CtaBar from '@/components/CtaBar.vue';
import { ref, computed } from 'vue';

const isblack = ref(false);
let inputname = ref(''); // 데이터 바인딩을 위한 변수

// 입력 제한 함수
const limitInput = (event) => {
  if (event.target.value.length > 10) {
    inputname.value = event.target.value.slice(0, 10); // 10자 이후의 문자열을 제거
  }
  isblack.value = inputname.value.length >= 1;
  console.log(isblack.value);
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
  font-size: 50px;
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
</style>
