<template>
  <div>
    <TopbarWithIcontokyo titleText="일주일 도쿄여행" class="topbar" />
  </div>
  <div class="date">2024. 06. 20</div>
  <div>
    <HistoryListItemNoCheck
      list="관광"
      number="- 5,109"
      number2="- 580"
      img="/src/assets/관광.png"
    />

    <HistoryListItemNoCheck
      list="현금"
      number="+ 100,000"
      number2="+ 11,392.77"
      img="/src/assets/현금.png"
    />
    <HistoryListItemNoCheck
      list="현금"
      number="+ 100,000"
      number2="+ 11,392.77"
      img="/src/assets/현금.png"
    />
    <HistoryListItemNoCheck
      list="현금"
      number="+ 100,000"
      number2="+ 11,392.77"
      img="/src/assets/현금.png"
    />
    <div class="result-box">
      <div class="spent-money">
        <div class="spent-money-title">사용한 금액</div>
        <div class="spent-money-amount">30,900</div>
      </div>
    </div>
  </div>
  <div>
    <CtaBarBlackSiwan inputname="추가하기" />
  </div>
</template>

<script setup>
import TopbarWithIcontokyo from "@/components/TopbarWithIcon-tokyo.vue";
import HistoryListItemNoCheck from "@/components/HistoryListItemNoCheck.vue";
import CtaBarSiwan from "@/components/CtaBar-siwan.vue";
import CtaBarBlackSiwan from "@/components/CtaBarBlack-siwan.vue";
</script>

<style scoped>
.date {
  box-sizing: border-box;
  width: 1080px;
  height: 96px;
  background: #f5f6f7;

  color: #8892a0;
  font-size: 40px;

  font-weight: 500;
  line-height: 40px;
  padding-left: 1.5em;
  line-height: 96px;

  text-align: left;
}
.result-box {
  width: 100%;
  height: 220px;
  display: flex;
  background-color: #eff4fe;
  margin-top: 660px;
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
</style>
