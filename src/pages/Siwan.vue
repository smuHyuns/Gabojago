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
  padding-top: 20vh;
}
.top {
  font-family: Pretendard;
  width: 100%;
  font-style: normal;
  font-weight: 700;
  font-size: 50px;
  line-height: 1.4;
  text-align: left;
  width: 965px;
  margin: 0 auto;
  color: #353b43;
}
.middle {
  width: 965px;
  height: 152px;
  padding: 46px 12px;
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
  width: 1080px;
  height: 2340px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 0 auto;
  background-color: #fff;
}

.down {
  position: absolute;
  bottom: 30px;
  left: 50%;
  transform: translateX(-50%);
}
</style>
