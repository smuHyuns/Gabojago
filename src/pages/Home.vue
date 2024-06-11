<template>
  <div>
    <Topbar titleText="여행지 제목" class="topbar" />
    <CalendarTopbar titleText="2024년 5월" />
  </div>
</template>

<script setup>
import Topbar from '@/components/Topbar.vue';
import CalendarTopbar from '@/components/CalendarTopbar.vue';
</script>

<style scoped>
.topbar {
  margin-bottom: 50px;
}
</style>
