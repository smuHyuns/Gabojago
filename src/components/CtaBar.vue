<template>
  <div>
    <button :class="isblack" @click="addSomething">{{ inputname }}</button>
  </div>
</template>

<script setup>
import { ref, computed, defineProps } from 'vue';

const props = defineProps({
  inputname: String,
  on: Boolean,
});

function addSomething() {
  console.log(props.inputname);
}

const isblack = computed(() => (props.on ? 'cta-bar-black' : 'cta-bar'));
</script>

<style scoped>
.cta-bar {
  width: 965px;
  height: 152px;
  margin-bottom: 133px;
  background: #eaecef;
  font-size: 46px;
  color: #8892a0;
  border-radius: 30px;
  border: none;
  /* 글자 관련 CSS 속성 */
}

.cta-bar-black {
  width: 965px;
  height: 152px;
  /* padding: 46px 11px; */
  background: #3e444e;
  color: #ffffff;
  border-radius: 30px;
  /* 글자 관련 CSS 속성 */
  font-style: normal;
  font-weight: 500;
  font-size: 46px;
  line-height: 1.3;
  text-align: center; /* 글자 수평 중앙 정렬 */
}
</style>
