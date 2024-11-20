<template>
  <div v-if="trip">
    <TopbarWithIcon
      :titleText="trip.describe || '여행 정보 없음'"
      class="topbar"
    />
    <div class="calendar-box">
      <CalTokyo
        :startPeriod="trip.startPeriod"
        :endPeriod="trip.endPeriod"
        :tripId="trip.id"
      />
    </div>
    <div class="result-box">
      <div class="spent-money">
        <div class="spent-money-title">사용한 금액</div>
        <div class="spent-money-amount">
          {{ trip.usedBudget?.toLocaleString() || '0' }}원
        </div>
      </div>
      <div class="remain-money">
        <div class="remain-money-title">남은 금액</div>
        <div class="remain-money-amount">
          {{ trip.remainingBudget?.toLocaleString() || '0' }}원
        </div>
      </div>
    </div>
    <CtaBarBlackSiwan class="ctabarblacksiwan" inputname="추가하기" />
  </div>
  <div v-else>
    <p>Loading...</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import TopbarWithIcon from '@/components/TopbarWithIcon.vue';
import CalTokyo from '@/components/CalTokyo.vue';
import CtaBarBlackSiwan from '@/components/CtaBarBlack-siwan.vue';

const route = useRoute();
const trip = ref(null);

onMounted(async () => {
  const tripId = route.params.tripId;
  try {
    const response = await fetch('/db.json'); // JSON 파일 경로 확인
    if (!response.ok) {
      console.error(`HTTP error! status: ${response.status}`);
      return;
    }
    const data = await response.json();
    const userTrips = data.users[0].trips;

    trip.value = userTrips.find((trip) => trip.id === parseInt(tripId)) || null;
  } catch (error) {
    console.error('Error fetching trip data:', error);
  }
});
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
