<template>
  <div class="page-container">
    <div class="top">여행자님의<br />닉네임을 알려주세요!</div>
    <input
      type="text"
      placeholder="10자 이내로 입력해주세요."
      class="middle"
      v-model="inputname"
    />

    <CtaBar class="down" inputname="click me!" />
  </div>
</template>

<script setup>
import CtaBar from "@/components/CtaBarBlack.vue";
</script>

<style scoped>
.top {
  font-family: Pretendard;
  /* 여행자님의 닉네임 알려주세요! (text) */
  width: 194px;
  height: 72px;

  font-family: Pretendard;
  font-style: normal;
  font-weight: 700;
  font-size: 26px;
  line-height: 1.4;
  text-align: left;
  transform: translateX(-50%);
  position: relative;
  left: 50%;

  color: #353b43;
}
.middle {
  width: 300px;
  height: 60px;
  padding: 18px 20px;

  background: #f5f6f7;

  border-radius: 16px;

  /* 10자 이내로 입력해주세요 (text) */
  width: 184px;
  height: 23px;

  font-family: Pretendard;
  font-style: normal;
  font-weight: 500;
  font-size: 18px;
  line-height: 1.3;
  text-align: center;
  left: 50%;
  position: relative;
  transform: translateX(-50%);

  color: #000;
}
.page-container {
  height: inherit;
  position: relative;
}

.down {
  /* 아래는 수직으로 중앙 정렬을 위한 코드 */
  position: absolute;
  bottom: 30px;
  left: 50%;
  transform: translateX(-50%);
}
</style>
