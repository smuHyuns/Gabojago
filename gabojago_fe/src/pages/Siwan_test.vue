<template>
  <div>
    <TopbarWithIcontokyo
      :titleText="trip?.describe || '지출 내역'"
      class="topbar"
    />
    <div class="date">{{ selectedDate }}</div>
    <div class="testbox">
      <div v-for="expense in filteredExpenses" :key="expense.description">
        <HistoryListItemNoCheck
          :list="expense.description"
          :number="expense.amount"
          :number2="expense.convertedAmount"
          :img="getCategoryImage(expense.category)"
          :type="expense.type"
        />
      </div>
    </div>
    <div class="result-box">
      <div class="spent-money">
        <div class="spent-money-title">사용한 금액</div>
        <div class="spent-money-amount">
          {{ usedBudget.toLocaleString() }}원
        </div>
      </div>
    </div>
    <CtaBarBlackSiwan
      class="ctabarblacksiwan"
      inputname="추가하기"
      @click="navigateToAddPayment"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';
import TopbarWithIcontokyo from '@/components/Trip/TopbarWithIcon_delete.vue';
import HistoryListItemNoCheck from '@/components/compo/HistoryListItemNoCheck.vue';
import CtaBarBlackSiwan from '@/components/Trip/CtaBarBlack-siwan.vue';

const route = useRoute();
const router = useRouter();
const selectedDate = route.params.date;
const tripId = route.params.tripId;
const trip = ref(null);
const filteredExpenses = ref([]);
const usedBudget = ref(0);

onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:3000/users/08ac');
    const user = response.data;
    const userTrips = user.trips;
    trip.value = userTrips.find((trip) => trip.id === parseInt(tripId));
    if (trip.value) {
      filteredExpenses.value = trip.value.expenses.filter(
        (expense) => expense.date === selectedDate
      );

      usedBudget.value = trip.value.expenses
        .filter((expense) => expense.type === '지출')
        .reduce((sum, expense) => sum + (expense.convertedAmount || 0), 0);

      // 여기서 trip 객체의 usedBudget과 remainingBudget 값을 업데이트하고 있습니다.
      trip.value.usedBudget = usedBudget.value;
      trip.value.remainingBudget =
        trip.value.totalBudget - trip.value.usedBudget;

      // 업데이트된 값을 DB에 저장합니다.
      await axios.put(`http://localhost:3000/users/08ac`, user);
    } else {
      console.error('Trip not found');
    }
  } catch (error) {
    console.error('Failed to fetch or update data:', error);
  }
});

function navigateToAddPayment() {
  router.push({
    name: 'AddPaymentFromDate',
    query: {
      tripId,
      selectedDate,
    },
  });
}

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
.testbox {
  height: 1500px;
  overflow-y: scroll;
}
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
