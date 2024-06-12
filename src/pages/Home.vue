<template>
  <div>
    <Topbar titleText="여행지 제목" class="topbar" />
    <CalendarTopbar titleText="2024년 5월" class="calendarTopbar" />
    <div class="calendar-container">
      <h3><router-link to="/hyunsoo">hyunsoo 이동</router-link></h3>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import Topbar from '@/components/Topbar.vue';
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
