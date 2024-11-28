<template>
  <div class="viewport">
    <TopbarWithIcon
      :titleText="trip?.describe || '지출 내역'"
      :tripId="tripId"
      :selectedDate="selectedDate"
      class="topbar"
    />
    <div class="print-box">
      <div v-for="(expenses, date) in groupedExpenses" :key="date">
        <div class="date">{{ date }}</div>
        <div v-for="expense in expenses" :key="expense.transactionId">
          <HistoryListItemNoCheck
            :list="mapExpenseType(expense.expenseType)"
            :number="expense.exchangeAmount"
            :number2="expense.expenseAmount"
            :img="getCategoryImage(mapExpenseType(expense.expenseType))"
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
      inputname="추가하기"
      @click="navigateToAddPayment"
    />
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';
import TopbarWithIcon from '@/components/Trip/TopbarWithIcon_delete.vue';
import HistoryListItemNoCheck from '@/components/compo/HistoryListItemNoCheck.vue';
import CtaBarBlackSiwan from '@/components/compo/CtaBarBlack-siwan.vue';
import { useAuthStore } from '@/stores/auth';

const route = useRoute();
const router = useRouter();
const authStore = useAuthStore();

const BASEURL = 'http://localhost:8080/transaction/detail-day-transaction';
const tripId = route.params.tripId; // 여행 ID 가져오기
const selectedDate = route.query.date; // 선택된 날짜 가져오기

const expenses = ref([]); // 백엔드에서 받아온 거래 데이터 저장
const totalExpense = ref(0);

// 백엔드 데이터 요청
const fetchTripExpense = async () => {
  try {
    const response = await axios.get(BASEURL, {
      headers: { Authorization: `Bearer ${authStore.token}` },
      params: {
        tripId: tripId,
        tripDate: selectedDate,
      },
    });
    expenses.value = response.data;

    // 총 지출 계산
    totalExpense.value = response.data.reduce(
      (sum, expense) => sum + (expense.expenseAmount || 0),
      0
    );
  } catch (error) {
    console.error('정보 불러오기 실패:', error.response?.data || error.message);
  }
};

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

// ExpenseType을 사용자 친화적인 이름으로 매핑
const mapExpenseType = (type) => {
  const expenseTypes = ['관광', '교통', '쇼핑', '숙박', '음식', '항공', '기타'];
  return expenseTypes[type] || '알 수 없음';
};

const getCategoryImage = (category) => {
  // 카테고리 매핑
  const categoryMap = {
    관광: '관광',
    교통: '교통',
    쇼핑: '쇼핑',
    숙박: '숙박',
    음식: '음식',
    항공: '항공',
    기타: '기타',
  };

  // 매핑된 카테고리를 가져오기
  const mappedCategory = categoryMap[category] || '기타';

  try {
    // 이미지 경로 생성
    const imagePath = new URL(
      `/src/assets/icons/${mappedCategory}.png`,
      import.meta.url
    ).href;
    console.log('Category:', category); // 전달된 category 값
    console.log('Mapped Category:', mappedCategory); // 매핑된 카테고리 값
    console.log('이미지 경로:', imagePath); // 생성된 이미지 경로
    return imagePath;
  } catch (error) {
    console.error('이미지 로드 실패:', error);
    // 기본 이미지 경로 반환
    return new URL('/src/assets/icons/기타.png', import.meta.url).href;
  }
};

// 결제 추가 페이지로 이동
const navigateToAddPayment = () => {
  router.push({
    name: 'AddPaymentFromDate',
    query: {
      tripId,
      selectedDate,
    },
  });
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
