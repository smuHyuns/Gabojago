<template>
  <div>
    <TopbarWithIcon titleText="일주일 도쿄여행" class="topbar" />
    <div class="calendar-box">
      <Cal />
    </div>
    <div>
      <CtaBarSiwan class="ctawhite" inputname="사용한 금액  남은 금액" />
    </div>
    <CtaBarBlackSiwan class="ctabarblacksiwan" inputname="추가하기" />
  </div>
</template>

<script setup>
import TopbarWithIcon from "@/components/TopbarWithIcon.vue";
import Cal from "@/components/Cal.vue";
import CtaBarSiwan from "@/components/CtaBar-siwan.vue";
import CtaBarBlackSiwan from "@/components/CtaBarBlack-siwan.vue";
// 반응형 변수 선언
</script>

<style scoped>
.ctabarblacksiwan {
  text-align: center;
  margin-bottom: 500px;
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
