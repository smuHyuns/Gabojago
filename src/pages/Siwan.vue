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

    <CtaBar class="down" inputname="click me!" />
  </div>
</template>

<script setup>
import CtaBar from "@/components/CtaBarBlack.vue";

let inputname = ""; // 데이터 바인딩을 위한 변수

// 입력 제한 함수
const limitInput = (event) => {
  if (event.target.value.length > 10) {
    event.target.value = event.target.value.slice(0, 10); // 10자 이후의 문자열을 제거
  }
  inputname = event.target.value; // 데이터 업데이트
};
</script>

<style scoped>
.container {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  margin-top: 20vh;
}
.top {
  font-family: Pretendard;
  width: 100%;
  font-style: normal;
  font-weight: 700;
  font-size: 26px;
  line-height: 1.4;
  text-align: center;
  color: #353b43;
}
.middle {
  width: 300px;
  height: 60px;
  padding: 18px 20px;
  margin-top: 20px;
  background: #f5f6f7;
  border-radius: 16px;
  font-family: Pretendard;
  font-style: normal;
  font-weight: 500;
  font-size: 18px;
  line-height: 1.3;
  text-align: center;
  color: #000;
}
.page-container {
  height: inherit;
  position: relative;
}

.down {
  position: absolute;
  bottom: 30px;
  left: 50%;
  transform: translateX(-50%);
}
</style>
