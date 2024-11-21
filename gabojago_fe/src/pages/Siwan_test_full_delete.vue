<template>
  <div>
    <TopbarWithIcontokyo :titleText="trip?.describe || '지출 내역'" :tripId="tripId" class="topbar" />
    <div class="print-box">
      <div v-for="(expenses, date) in groupedExpenses" :key="date">
        <div class="date">{{ date }}</div>
        <div v-for="expense in expenses" :key="expense.id">
          <DeleteButtonSiwan
            :countryName="expense.description"
            :flagSrc="getCategoryImage(expense.category)"
            :isSelected="selectedExpenses.includes(expense.id)"
            :number="expense.convertedAmount"
            :number2="expense.amount"
            @update:isSelected="updateSelectedExpenses(expense.id)"
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
    <CtaBarBlackSiwan class="ctabarblacksiwan" inputname="삭제하기" @click="deleteSelectedExpenses" />
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';
import TopbarWithIcontokyo from '@/components/TopbarWithIcon-tokyo.vue';
import DeleteButtonSiwan from '@/components/DeleteButton-siwan.vue';
import CtaBarBlackSiwan from '@/components/CtaBarBlack-siwan.vue';

const route = useRoute();
const router = useRouter();
const selectedDate = route.params.date;
const tripId = route.params.tripId;
const userId = '08ac';
const trip = ref(null);
const filteredExpenses = ref([]);
const selectedExpenses = ref([]);
const usedBudget = ref(0);

const sortedExpenses = computed(() => {
  return filteredExpenses.value.sort((a, b) => new Date(a.date) - new Date(b.date));
});

const groupedExpenses = computed(() => {
  return sortedExpenses.value.reduce((groups, expense) => {
    const date = expense.date.split('T')[0];
    if (!groups[date]) {
      groups[date] = [];
    }
    groups[date].push(expense);
    return groups;
  }, {});
});

onMounted(async () => {
  try {
    const response = await axios.get(`http://localhost:3000/users/${userId}`);
    const user = response.data;
    const userTrips = user.trips;
    console.log('Trip ID:', tripId);
    trip.value = userTrips.find((trip) => trip.id == tripId);
    if (trip.value) {
      console.log('Trip found:', trip.value);
      filteredExpenses.value = trip.value.expenses.sort((a, b) => new Date(a.date) - new Date(b.date));

      usedBudget.value = trip.value.expenses.filter((expense) => expense.type === '지출').reduce((sum, expense) => sum + (expense.convertedAmount || 0), 0);
    } else {
      console.error('Trip not found with ID:', tripId);
    }
  } catch (error) {
    console.error('Failed to fetch or update data:', error);
  }
});

function updateSelectedExpenses(expenseId) {
  if (selectedExpenses.value.includes(expenseId)) {
    selectedExpenses.value = selectedExpenses.value.filter((id) => id !== expenseId);
  } else {
    selectedExpenses.value.push(expenseId);
  }
}

async function deleteSelectedExpenses() {
  try {
    const user = (await axios.get(`http://localhost:3000/users/${userId}`)).data;
    const tripToUpdate = user.trips.find((t) => t.id === parseInt(tripId));

    if (tripToUpdate) {
      const updatedExpenses = tripToUpdate.expenses.filter((expense) => !selectedExpenses.value.includes(expense.id));
      tripToUpdate.expenses = updatedExpenses;

      await axios.put(`http://localhost:3000/users/${userId}`, user);

      alert('선택된 지출 내역이 삭제되었습니다.');
      filteredExpenses.value = updatedExpenses;
      selectedExpenses.value = [];
    } else {
      console.error('Trip not found for updating expenses');
    }
  } catch (error) {
    console.error('Error deleting expenses:', error);
    alert('지출 내역 삭제 중 오류가 발생했습니다.');
  }
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
  height: 1600px;
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
