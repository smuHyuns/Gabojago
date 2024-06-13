<template>
  <div>
    <TopbarWithIcontokyo :titleText="trip?.describe || '지출 내역'" class="topbar" />
    <div class="date">{{ selectedDate }}</div>
    <div v-for="expense in expenses" :key="expense.id">
      <HistoryListItemNoCheck
        :list="expense.description"
        :number="formatAmount(expense.amount)"
        :number2="formatAmount(expense.convertedAmount)"
        :img="getCategoryImage(expense.category)"
      />
    </div>
    <div class="result-box">
      <div class="spent-money">
        <div class="spent-money-title">사용한 금액</div>
        <div class="spent-money-amount">{{ formatAmount(totalSpent.value) }}</div>
      </div>
    </div>
    <CtaBarBlackSiwan class="ctabarblacksiwan" inputname="추가하기" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import TopbarWithIcontokyo from '@/components/TopbarWithIcon-tokyo.vue';
import HistoryListItemNoCheck from '@/components/HistoryListItemNoCheck.vue';
import CtaBarBlackSiwan from '@/components/CtaBarBlack-siwan.vue';

const route = useRoute();
const selectedDate = route.params.date;
const tripId = route.params.tripId;
const trip = ref(null);
const expenses = ref([]);
const totalSpent = ref(0);

onMounted(async () => {
  const response = await fetch('/db.json'); // JSON 파일 경로 확인
  if (!response.ok) {
    console.error(`HTTP error! status: ${response.status}`);
    return;
  }
  const data = await response.json();
  const userTrips = data.users[0].trips;
  trip.value = userTrips.find((trip) => trip.id === parseInt(tripId));
  if (trip.value) {
    expenses.value = trip.value.expenses.filter((expense) => expense.date === selectedDate);
    totalSpent.value = expenses.value.reduce((sum, expense) => sum + (expense.amount || 0), 0);
  }
});

function formatAmount(amount) {
  return amount ? `${amount.toLocaleString()}` : '0';
}

function getCategoryImage(category) {
  try {
    return new URL(`/src/assets/${category}.png`, import.meta.url).href;
  } catch (e) {
    return new URL(`/src/assets/default.png`, import.meta.url).href;
  }
}
</script>

<style scoped>
.date {
  box-sizing: border-box;
  width: 100%;
  height: 96px;
  background: #f5f6f7;
  color: #8892a0;
  font-size: 40px;
  font-weight: 500;
  padding-left: 1.5em;
  display: flex;
  align-items: center;
}
.result-box {
  width: 100%;
  height: 220px;
  display: flex;
  background-color: #eff4fe;
  margin-top: 20px;
  justify-content: center;
  align-items: center;
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
}

.spent-money-amount {
  text-align: center;
  font-size: 60px;
  font-weight: 700;
}
</style>
