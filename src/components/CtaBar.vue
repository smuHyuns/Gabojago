<template>
  <div>
    <button class="cta-bar" @click="addSomething">{{ inputname }}</button>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';

const props = defineProps({
  inputname: String,
});

function addSomething() {
  console.log(props.inputname);
}
</script>

<style scoped>
.cta-bar {
  width: 965px;
  height: 152px;
  margin-bottom: 133px;
  background: #eaecef;
  color: #8892a0;
  border-radius: 30px;
  border: none;
  /* 글자 관련 CSS 속성 */
  font-family: Pretendard;
  font-style: normal;
  font-weight: 500;
  font-size: 46px;
  line-height: 1.3;
  text-align: center; /* 글자 수평 중앙 정렬 */
}
</style>
