<template>
  <div>
    <button class="cta-bar">
      <span class="add">{{ inputname }}</span>
    </button>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';

const props = defineProps({
  inputname: String,
});
</script>

<style scoped>
.cta-bar {
  position: absolute;
  bottom: 0px; /* 바닥으로부터 45px 위에 고정 */
  left: 50%;
  transform: translateX(-50%);
  width: 1080px;
  height: 262px;
  /* padding: 46px 11px; */
  background: #3e444e;
  color: #ffffff;
  border-radius: 30px;
  /* 글자 관련 CSS 속성 */
  font-style: normal;
  font-weight: 600;
  font-size: 46px;
  line-height: 1.3;
  border-radius: 0px;
  border: none;
}

.add {
  display: flex;
  justify-content: center;
  padding-bottom: 52px;
}
</style>
