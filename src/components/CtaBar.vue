<template>
  <div class="ctaBar">
    <button :class="isblack" @click="addSomething">{{ inputname }}</button>
  </div>
</template>

<script setup>
import { ref, computed, defineProps } from 'vue';

const props = defineProps({
  inputname: String,
  on: Boolean,
});

const isblack = computed(() => (props.on ? 'cta-bar-black' : 'cta-bar'));
</script>

<style scoped>
.cta-bar {
  position: absolute;
  bottom: 110px; /* 바닥으로부터 45px 위에 고정 */
  left: 50%;
  transform: translateX(-50%);
  width: 965px;
  height: 152px;
  background: #eaecef;
  font-size: 46px;
  color: #8892a0;
  font-style: normal;
  font-weight: 600;
  font-size: 46px;
  line-height: 1.3;
  text-align: center;
  border-radius: 30px;
  border: none;
}

.cta-bar-black {
  position: absolute;
  bottom: 110px; /* 바닥으로부터 45px 위에 고정 */
  left: 50%;
  transform: translateX(-50%);
  width: 965px;
  height: 152px;
  /* padding: 46px 11px; */
  background: #3e444e;
  color: #ffffff;
  border-radius: 30px;
  /* 글자 관련 CSS 속성 */
  font-style: normal;
  font-weight: 600;
  font-size: 46px;
  line-height: 1.3;
  text-align: center; /* 글자 수평 중앙 정렬 */
  border-radius: 30px;
  border: none;
}
</style>
