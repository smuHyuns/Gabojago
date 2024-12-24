<template>
  <div class="viewport">
    <div v-if="isDataLoaded">
      <TopbarWithIcon
        :titleText="trip.describe || '여행 정보 없음'"
        :tripId="trip.id"
        :selectedDate="selectedDate"
        class="topbar"
      />
      <div v-if="isDataLoaded">
        <div class="calendar-box">
          <Calendar
            :startPeriod="trip.startPeriod"
            :endPeriod="trip.endPeriod"
            :tripId="trip.id"
            @date-click="handleDateClick"
          />
        </div>
      </div>

      <div class="result-box">
        <div class="spent-money">
          <div class="spent-money-title">사용한 금액</div>
          <div class="spent-money-amount">
            {{ trip.usedBudget.toLocaleString() }}원
          </div>
        </div>
        <div class="remain-money">
          <div class="remain-money-title">남은 금액</div>
          <div class="remain-money-amount">
            {{ trip.remainingBudget.toLocaleString() }}원
          </div>
        </div>
      </div>
      <CtaBarBlackSiwan
        class="ctabarblacksiwan"
        inputname="추가하기"
        @click="navigateToTripAddPayment"
      />
    </div>
    <div v-else>
      <p>Loading...</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import TopbarWithIcon from '@/components/used/TopbarWithIcon.vue';
import Calendar from '@/components/used/TripCalendar.vue';
import CtaBarBlackSiwan from '@/components/used/CtaBarBlack-siwan.vue';
import { useAuthStore } from '@/stores/auth';
import axios from 'axios';
import { getTripDetail } from '@/api/trip';

const BASEURL = 'http://localhost:8080';

const route = useRoute();
const router = useRouter();
const authStore = useAuthStore();

const trip = ref({
  id: null,
  describe: ref(null),
  startPeriod: ref(null),
  endPeriod: ref(null),
  usedBudget: 0,
  remainingBudget: 0,
});

const isDataLoaded = ref(false);

const selectedDate = ref(null);

const getTodayDate = () => {
  const today = new Date();
  const year = today.getFullYear();
  const month = String(today.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작
  const day = String(today.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};

const logTrip = (trip) => {
  console.log(trip);
};

const fetchTripInfo = async () => {
  console.log('tripId : ', route.params.tripId);
  console.log(getTodayDate());
  console.log(authStore.token);

  try {
    console.log(route.params.tripId);
    const response = await getTripDetail(route.params.tripId);

    console.log('초기 여행정보 불러오기');
    if (response) {
      Object.assign(trip.value, {
        id: route.params.tripId,
        describe: response.data.description || '여행 설명이 없습니다',
        startPeriod: response.data.startPeriod || null,
        endPeriod: response.data.endPeriod || null,
        usedBudget: response.data.totalExpense || 0,
        remainingBudget: response.data.tripBudget || 0,
      });
      isDataLoaded.value = true;
    }
  } catch (error) {
    console.error('여행 정보 불러오기 실패:', error);
  }
};

const fetchTripExpense = async (date) => {
  try {
    const formattedDate = `${new Date(date).getFullYear()}-${String(
      new Date(date).getMonth() + 1
    ).padStart(2, '0')}-${String(new Date(date).getDate()).padStart(2, '0')}`;
    selectedDate.value = formattedDate;
    console.log('선택한 날짜:', formattedDate);

    const response = await axios.get(`${BASEURL}/transaction/detail-day`, {
      headers: { Authorization: `Bearer ${authStore.token}` },
      params: {
        tripId: route.params.tripId,
        tripDate: formattedDate,
      },
    });

    if (response.data) {
      Object.assign(trip.value, {
        usedBudget: response.data.totalExpense || 0,
      });
    }
  } catch (error) {
    console.log(
      '선택 날짜 정보 불러오기 실패 : ',
      error.response?.data || error.message
    );
  }
};

const handleDateClick = async (date) => {
  selectedDate.value = date;
  await fetchTripExpense(date);
};

const navigateToTripAddPayment = () => {
  router.push({
    name: 'Detail_AddPayment',
    params: { tripId: route.params.tripId },
    query: { date: selectedDate.value },
  });
};

onMounted(() => {
  fetchTripInfo();
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

.calendar-box {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  box-sizing: border-box;
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
  box-sizing: border-box;
  aspect-ratio: 1 / 1;
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
