<template>
  <div>
    <button class="cta-bar" @click="addSomething">{{ inputname }}</button>
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
.cta-bar {
  width: 975px;
  height: 77px;
  padding: 46px 52px;
  background: #3e444e;
  border-radius: 10px;
  /* 글자 관련 CSS 속성 */
  font-family: Pretendard;
  font-style: normal;
  font-weight: 600;
  font-size: 16px;
  line-height: 1.3;
  text-align: center; /* 글자 수평 중앙 정렬 */
}
</style>
