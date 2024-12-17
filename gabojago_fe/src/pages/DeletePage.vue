<template>
  <div>
    <TopbarWithIcon-tokyo
      :titleText="trip?.describe || '지출 내역'"
      :tripId="tripId"
      class="topbar"
    />
    <div class="print-box">
      <div class="date">{{ selectedDate }}</div>
      <div v-for="expense in filteredExpenses" :key="expense.description">
        <DeleteButton-siwan
          :countryName="expense.description"
          :flagSrc="getCategoryImage(expense.category)"
          :isSelected="selectedExpenses.includes(expense.description)"
          :number="expense.convertedAmount"
          :number2="expense.amount"
          @update:isSelected="updateSelectedExpenses(expense.description)"
        />
      </div>
    </div>
    <CtaBarBlackSiwan
      class="ctabarblacksiwan"
      inputname="삭제하기"
      @click="deleteSelectedExpenses"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';
import TopbarWithIconTokyo from '@/components/Trip/TopbarWithIcon_delete.vue';
import DeleteButtonSiwan from '@/components/compo/DeleteButton-siwan.vue';
import CtaBarBlackSiwan from '@/components/Trip/CtaBarBlack-siwan.vue';

const route = useRoute();
const router = useRouter();
const tripId = route.params.tripId;
const trip = ref(null);
const filteredExpenses = ref([]);
const selectedExpenses = ref([]);
const selectedDate = route.params.date;
const userId = '08ac'; // 사용자 ID를 0으로 설정합니다.

onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:3000/users'); // JSON 서버 경로 확인
    const data = response.data;
    console.log('Fetched data:', data); // 데이터 로깅
    const userTrips = data.find((user) => user.id === userId).trips;
    trip.value = userTrips.find((trip) => trip.id === parseInt(tripId));
    console.log('Found trip:', trip.value); // trip 로깅
    if (trip.value) {
      filteredExpenses.value = trip.value.expenses;
      console.log('Filtered expenses:', filteredExpenses.value); // filteredExpenses 로깅
    }
  } catch (error) {
    console.error('Error fetching trip data:', error);
  }
});

function updateSelectedExpenses(expenseDescription) {
  if (selectedExpenses.value.includes(expenseDescription)) {
    selectedExpenses.value = selectedExpenses.value.filter(
      (desc) => desc !== expenseDescription
    );
  } else {
    selectedExpenses.value.push(expenseDescription);
  }
}

async function deleteSelectedExpenses() {
  try {
    const user = (await axios.get(`http://localhost:3000/users/${userId}`))
      .data;
    const updatedTrips = user.trips.map((t) => {
      if (t.id === parseInt(tripId)) {
        return {
          ...t,
          expenses: t.expenses.filter(
            (expense) => !selectedExpenses.value.includes(expense.description)
          ),
        };
      }
      return t;
    });

    const response = await axios.patch(
      `http://localhost:3000/users/${userId}`,
      {
        trips: updatedTrips,
      }
    );

    if (response.status === 200) {
      alert('선택된 지출 내역이 삭제되었습니다.');
      // 삭제 후 로컬 상태를 업데이트
      filteredExpenses.value = trip.value.expenses.filter(
        (expense) => !selectedExpenses.value.includes(expense.description)
      );
      selectedExpenses.value = [];
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
  height: 2000px;
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
