<template>
  <div>
    <Topbar titleText="여행지 제목" class="topbar" />
    <CalendarTopbar titleText="2024년 5월" class="calendarTopbar" />
    <div class="calendar-box"></div>
    <!-- <VCalendar /> -->
  </div>
</template>

<script setup>
import Topbar from '@/components/Topbar.vue';
</script>

<style scoped>
.topbar {
  margin-bottom: 20px;
}

.calendarTopbar {
  margin-bottom: 20px;
  text-align: center;
}

.calendar-box {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 1000px;
  margin-top: 70px;
  border: 1px solid black;
}
</style>
