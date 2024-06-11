<template>
  <div class="container">
    <button class="down" @click="addSomething">{{ inputname }}</button>
  </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  inputname: String,
});

function addSomething() {
  console.log(props.inputname);
}
</script>

<style scoped>
.container {
  display: flex; /* Flexbox 사용 */
  justify-content: center; /* 가로 방향으로 중앙 정렬 */
  align-items: flex-end; /* 세로 방향으로 아래 정렬 */
  height: 100vh; /* 화면 전체 높이 */
}

.down {
  width: 375px;
  height: 77px;
  padding: 12px 16px;

  background: #3e444e;

  border-radius: 10px;

  /* 다음으로 (text) */
  font-family: Pretendard;
  font-style: normal;
  font-weight: 600;
  font-size: 16px;
  line-height: 1.3;
  text-align: center; /* 수평으로 중앙 정렬 */

  /* 아래는 수직으로 중앙 정렬을 위한 코드 */
  position: absolute;
  bottom: 30px;
  left: 50%;
  transform: translateX(-50%);
  background-color: #3e444e;
  color: #ffffff;
  padding: 30px;
  border: none;
  border-radius: 10px;
}
</style>
