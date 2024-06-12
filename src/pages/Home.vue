<template>
  <div>
    <CtaBar inputname="안녕하세요" />
  </div>
</template>

<script setup>
import CtaBar from '@/components/CtaBar.vue';
import { ref } from 'vue';
CtaBar;
</script>

<style scoped>
body {
  margin: 0;
  font-family: Arial, sans-serif;
}

.topbar {
  margin-bottom: 20px;
}

.calendarTopbar {
  margin-bottom: 20px;
  text-align: center;
}

.calendar-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 1000px;
  margin-top: 70px;
  border: 1px solid black;
}

.v-date-picker {
  width: 1000px;
  height: 1000px;
}
</style>
