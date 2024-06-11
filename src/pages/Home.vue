<template>
  <div>
    <Topbar titleText="여행지 제목" />
    <div class="calendar-container">
      <VDatePicker v-model="date" expanded />
    </div>
  </div>
</template>

<script setup>
import Topbar from '@/components/Topbar.vue';
import { ref } from 'vue';
import 'v-calendar/style.css';

const date = ref(null);
</script>

<style scoped>
.calendar-container {
  width: 1000px;
  height: 1000px;
}
</style>
