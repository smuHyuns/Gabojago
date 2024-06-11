<template>
  <div class="container">
    <button class="down" @click="addSomething">{{ inputname }}</button>
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
.container {
  display: flex;
  justify-content: center;
  align-items: flex-end;
  height: 100vh;
}

.down {
  width: 965px;
  height: 152px;
  padding: 46.08px 11.52px;
  background: #eaecef;
  border-radius: 28.8px;
  font-family: Pretendard;
  font-style: normal;
  font-weight: 600;
  font-size: 46px;
  line-height: 1.3;
  text-align: center; /* Center align the text */
  color: #8892a0;
  border: none;

  display: flex; /* Use flexbox to center text vertically */
  align-items: center; /* Center text vertically */
  justify-content: center; /* Center text horizontally */

  position: absolute;
  bottom: 30px;
  left: 50%;
  transform: translateX(-50%);
}
</style>
