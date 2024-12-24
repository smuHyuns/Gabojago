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
            :type="expense.transactionType"
            :currency="currency"
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
      @click="navigateToTripAddPayment"
    />
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import TopbarWithIcon from '@/components/used/TopbarWithIcon_delete.vue';
import HistoryListItemNoCheck from '@/components/used/HistoryListItemNoCheck.vue';
import CtaBarBlackSiwan from '@/components/used/CtaBarBlack-siwan.vue';
import { getDetailDayTransaction } from '@/api/transaction';
import { getCurrency } from '@/api/trip';

const route = useRoute();
const router = useRouter();

const tripId = route.params.tripId;
const selectedDate = route.query.date;

const expenses = ref([]);
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

const mapExpenseType = (type) => {
  const expenseTypes = ['관광', '교통', '쇼핑', '숙박', '음식', '항공', '기타'];
  return expenseTypes[type] || '알 수 없음';
};

const getCategoryImage = (category) => {
  const categoryMap = {
    관광: '관광',
    교통: '교통',
    쇼핑: '쇼핑',
    숙박: '숙박',
    음식: '음식',
    항공: '항공',
    기타: '기타',
  };

  const mappedCategory = categoryMap[category] || '기타';

  try {
    const imagePath = new URL(
      `/src/assets/icons/${mappedCategory}.png`,
      import.meta.url
    ).href;
    console.log('Category:', category);
    console.log('Mapped Category:', mappedCategory);
    console.log('이미지 경로:', imagePath);
    return imagePath;
  } catch (error) {
    console.error('이미지 로드 실패:', error);
    return new URL('/src/assets/icons/기타.png', import.meta.url).href;
  }
};

const navigateToTripAddPayment = () => {
  router.push({
    name: 'Detail_AddPayment',
    params: { tripId: tripId.value },
    query: { date: route.query.date },
  });
};

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

.category-box .selected {
  filter: hue-rotate(371deg) brightness(1) saturate(7);
  color: white;
}
</style>
