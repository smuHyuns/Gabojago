<template>
  <div class="viewport">
    <TopbarWithIcon
      :titleText="trip?.describe || '지출 내역'"
      :tripId="tripId"
      class="topbar"
    />
    <div class="print-box">
      <div v-for="(expenses, date) in groupedExpenses" :key="date">
        <div class="date">{{ date }}</div>
        <div v-for="expense in expenses" :key="expense.transactionId">
          <TransactionDeleteBtn
            :description="mapExpenseType(expense.expenseType)"
            :flagSrc="getCategoryImage(expense.expenseType)"
            :isSelected="selectedExpenses.includes(expense.transactionId)"
            :number="expense.expenseAmount"
            :number2="expense.exchangeAmount"
            :type="expense.transactionType"
            :currency="currency"
            @update:isSelected="updateSelectedExpenses(expense.transactionId)"
          />
        </div>
      </div>
    </div>

    <div class="result-box">
      <div class="spent-money">
        <div class="spent-money-title">사용한 금액</div>
        <div class="spent-money-amount">
          {{ totalExpense.toLocaleString() }}원
        </div>
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
import { ref, onMounted, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';
import TopbarWithIcon from '@/components/used/TopbarWithIcon-deleteFull.vue';
import TransactionDeleteBtn from '@/components/used/TransactionDeleteBtn.vue';
import CtaBarBlackSiwan from '@/components/used/CtaBarBlack-siwan.vue';
import { useAuthStore } from '@/stores/auth';
import { getCurrency, getDetailDayTransaction } from '@/api/transaction';

const route = useRoute();
const router = useRouter();
const authStore = useAuthStore();

const BASEURL = 'http://localhost:8080/transaction';
const tripId = route.params.tripId; // 여행 ID 가져오기
const selectedDate = route.query.date; // 선택된 날짜 가져오기

const expenses = ref([]); // 백엔드에서 받아온 거래 데이터 저장
const selectedExpenses = ref([]); // 선택된 항목 ID 저장

const totalExpense = ref(0);
const currency = ref('');

const fetchTripExpense = async () => {
  try {
    console.log(tripId);

    const response = await getDetailDayTransaction(tripId, selectedDate);
    const data = await getCurrency(tripId);

    currency.value = data.currency;
    expenses.value = response;

    totalExpense.value = response
      .filter((expense) => expense.transactionType === '지출')
      .reduce((sum, expense) => sum + (expense.expenseAmount || 0), 0);
  } catch (error) {
    console.error('정보 불러오기 실패:', error.response?.data || error.message);
  }
};

async function deleteLog() {
  console.log('tripId : ', tripId);
  console.log('transactionIds : ', transactionIds.value);
  console.log();
}

//백엔드 삭제요청
// 선택된 지출 항목 삭제
async function deleteSelectedExpenses() {
  try {
    // 선택된 항목 삭제 요청
    await axios.delete(`${BASEURL}/delete`, {
      headers: { Authorization: `Bearer ${authStore.token}` },
      data: {
        tripId: tripId, // 여행 ID
        transactionIds: selectedExpenses.value, // 선택된 transactionId 배열 전달
      },
    });

    // 로컬 상태 업데이트: 선택된 항목 제거
    expenses.value = expenses.value.filter(
      (expense) => !selectedExpenses.value.includes(expense.transactionId)
    );

    // 총 지출 업데이트
    totalExpense.value = expenses.value.reduce(
      (sum, expense) => sum + (expense.expenseAmount || 0),
      0
    );

    // 성공 알림 및 초기화
    alert('선택된 지출 내역이 삭제되었습니다.');
    selectedExpenses.value = [];
  } catch (error) {
    console.error('지출 삭제 실패:', error.response?.data || error.message);
    alert('삭제 중 오류가 발생했습니다.');
  }
}

// 날짜별로 데이터를 그룹화
const groupedExpenses = computed(() => {
  return expenses.value.reduce((groups, expense) => {
    const date = expense.expenseDate;
    if (!groups[date]) {
      groups[date] = [];
    }
    groups[date].push(expense);
    return groups;
  }, {});
});

// 선택된 지출 항목 업데이트
function updateSelectedExpenses(expenseId) {
  if (selectedExpenses.value.includes(expenseId)) {
    selectedExpenses.value = selectedExpenses.value.filter(
      (id) => id !== expenseId
    );
  } else {
    selectedExpenses.value.push(expenseId);
  }
}

// ExpenseType을 사용자 친화적인 이름으로 매핑
const mapExpenseType = (type) => {
  const expenseTypes = ['관광', '교통', '쇼핑', '숙박', '음식', '항공', '기타'];
  return expenseTypes[type] || '알 수 없음';
};

const getCategoryImage = (type) => {
  const mappedCategory = mapExpenseType(type);
  try {
    return new URL(`/src/assets/icons/${mappedCategory}.png`, import.meta.url)
      .href;
  } catch (error) {
    return new URL('/src/assets/icons/기타.png', import.meta.url).href;
  }
};

// 컴포넌트 로드 시 데이터 요청
onMounted(() => {
  fetchTripExpense();
});
</script>

<style scoped>
.viewport {
  width: 1080px;
  height: 2340px;
  overflow: hidden;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 0 auto;
  background-color: #fff;
  position: relative;
  border: 1px solid black;
}

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
