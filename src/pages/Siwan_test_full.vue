<template>
  <div>
    <Siwan_test_full_delete :titleText="trip?.describe || '지출 내역'" :tripId="tripId" class="topbar" />
    <div class="print-box">
      <div v-for="(expenses, date) in groupedExpenses" :key="date">
        <div class="date">{{ date }}</div>
        <div v-for="expense in expenses" :key="expense.description">
          <HistoryListItemNoCheck
            :list="expense.description"
            :number="expense.amount"
            :number2="expense.convertedAmount"
            :img="getCategoryImage(expense.category)"
            :type="expense.type"
          />
        </div>
      </div>
    </div>

    <div class="result-box">
      <div class="spent-money">
        <div class="spent-money-title">사용한 금액</div>
        <div class="spent-money-amount">{{ usedBudget.toLocaleString() }}원</div>
      </div>
    </div>
    <CtaBarBlackSiwan class="ctabarblacksiwan" inputname="추가하기" @click="navigateToAddPayment" />
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';
import Siwan_test_full_delete from '@/components/TopbarWithIcon-deleteFull.vue';
import HistoryListItemNoCheck from '@/components/HistoryListItemNoCheck.vue';
import CtaBarBlackSiwan from '@/components/CtaBarBlack-siwan.vue';

const route = useRoute();
const router = useRouter();
const selectedDate = route.params.date; // route.params.date로 selectedDate를 가져옵니다.
const tripId = route.params.tripId; // route.params.tripId로 tripId를 가져옵니다.
const trip = ref(null);
const filteredExpenses = ref([]);
const usedBudget = ref(0);

const sortedExpenses = computed(() => {
  return filteredExpenses.value.sort((a, b) => new Date(a.date) - new Date(b.date));
});

// selectedDate 기준으로 groupedExpenses를 계산합니다.
const groupedExpenses = computed(() => {
  return sortedExpenses.value.reduce((groups, expense) => {
    const date = expense.date.split('T')[0]; // date 값을 'YYYY-MM-DD' 형식으로 사용
    if (!groups[date]) {
      groups[date] = [];
    }
    groups[date].push(expense);
    return groups;
  }, {});
});

onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:3000/users/08ac');
    const user = response.data;
    const userTrips = user.trips;
    console.log('Trip ID:', tripId); // tripId가 올바르게 전달되는지 확인
    trip.value = userTrips.find((trip) => trip.id == tripId); // tripId를 문자열로 비교합니다.
    if (trip.value) {
      console.log('Trip found:', trip.value); // trip이 제대로 찾아졌는지 확인
      // 모든 expenses를 날짜별로 정렬하여 filteredExpenses에 저장합니다.
      filteredExpenses.value = trip.value.expenses.sort((a, b) => new Date(a.date) - new Date(b.date));

      usedBudget.value = trip.value.expenses.filter((expense) => expense.type === '지출').reduce((sum, expense) => sum + (expense.convertedAmount || 0), 0);

      trip.value.usedBudget = usedBudget.value;
      trip.value.remainingBudget = trip.value.totalBudget - trip.value.usedBudget;

      await axios.put(`http://localhost:3000/users/08ac`, user);
    } else {
      console.error('Trip not found with ID:', tripId); // tripId에 해당하는 여행이 없을 때 오류 메시지 출력
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
.print-box {
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
