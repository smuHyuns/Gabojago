<template>
  <div>
    <Topbar titleText="여행지 제목" class="topbar" />
    <div class="calendar-box">
      <Cal />
    </div>
    <CtaBar class="ctabar" inputname="다음으로" />
  </div>
</template>

<script setup>
import Topbar from '@/components/Topbar.vue';
import Cal from '@/components/Cal.vue';
import CtaBar from '@/components/CtaBar.vue';
// 반응형 변수 선언
</script>

<style scoped>
.ctabar {
  text-align: center;
  margin-top: 740px;
}

.calendar-box {
  width: 100%;
  height: 100%; /* 원하는 높이로 조정하세요 */
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  box-sizing: border-box; /* padding 포함한 크기 계산 */
}

.calendar-container {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.calendar-box {
  width: 1000px;
  height: 1000px;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  box-sizing: border-box; /* padding 포함한 크기 계산 */
  aspect-ratio: 1 / 1; /* 1:1 비율을 유지 */
}
</style>
