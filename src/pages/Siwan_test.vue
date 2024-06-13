<template>
  <div>
    <TopbarWithIcon titleText="일주일 도쿄여행" class="topbar" />
    <div class="calendar-box">
      <Cal />
    </div>
    <div class="result-box">
      <div class="spent-money">
        <div class="spent-money-title">사용한 금액</div>
        <div class="spent-money-amount">30,900</div>
      </div>
      <div class="remain-money">
        <div class="remain-money-title">남은 금액</div>
        <div class="remain-money-amount">100,000</div>
      </div>
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

.result-box {
  width: 100%;
  height: 220px;
  display: flex;
  background-color: #eff4fe;
  margin-top: 620px;
}
.spent-money {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.spent-money-title {
  text-align: center;
  color: #8892a0;

  font-size: 40px;

  font-weight: 400;
  line-height: 52px;
  word-wrap: break-word;
}

.spent-money-amount {
  text-align: center;
  font-size: 60px;
  font-weight: 700;
}
.remain-money {
  justify-content: center;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.remain-money-title {
  text-align: center;
  color: #8892a0;
  font-size: 40px;

  font-weight: 400;
  line-height: 52px;
  word-wrap: break-word;
}

.remain-money-amount {
  text-align: center;
  color: #7c91ff;
  font-size: 60px;

  font-weight: 700;

  word-wrap: break-word;
}
</style>
