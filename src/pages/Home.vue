<template>
  <div>
    <Topbar titleText="여행지 제목" />
    <VDatePicker v-model="date" class="calendar" />
  </div>
</template>

<script setup>
import Topbar from '@/components/Topbar.vue';
import 'v-calendar/style.css';
</script>

<style scoped>
.calendar {
  width: 1000px;
  height: 1000px;
}
</style>
