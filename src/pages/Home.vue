<template>
  <div>
    <Topbar titleText="여행지 제목" />
  </div>
  <div class="calendar-container">
    <!-- VCalendar 컴포넌트로 변경 -->
    <VCalendar v-model="date" class="calendar" />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import Topbar from '@/components/Topbar.vue';
import 'bootstrap/dist/css/bootstrap.min.css';
// VCalendar 컴포넌트를 임포트
import VCalendar from 'v-calendar';

const date = ref(null);
</script>

<style scoped>
.calendar-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: calc(100vh - 60px); /* 화면 높이의 전체에서 Topbar의 높이를 뺀 값 */
}

.calendar {
  width: 100%;
  max-width: none; /* 최대 너비 제한을 없애서 화면 크기에 맞게 조정될 수 있도록 함 */
  height: auto; /* 높이를 자동으로 설정하여 내용에 맞게 조정될 수 있도록 함 */
}

@media (max-width: 1080px) {
  .calendar {
    width: 100%; /* 모바일 화면에서도 달력의 너비를 100%로 설정 */
  }
}
</style>
