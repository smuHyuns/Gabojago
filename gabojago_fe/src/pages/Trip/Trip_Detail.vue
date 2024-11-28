<template>
  <div class="viewport">
    <button @click="logTrip(trip)">버튼</button>
    <div v-if="isDataLoaded">
      <TopbarWithIcon
        :titleText="trip.describe || '여행 정보 없음'"
        :tripId="trip.id"
        :selectedDate="selectedDate"
        class="topbar"
      />
      <div class="calendar-box">
        <Calendar
          :startPeriod="trip.startPeriod"
          :endPeriod="trip.endPeriod"
          :tripId="trip.id"
          @date-click="handleDateClick"
        />
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
      <CtaBarBlackSiwan class="ctabarblacksiwan" inputname="추가하기" />
    </div>
    <div v-else>
      <p>Loading...</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import TopbarWithIcon from '@/components/Trip/TopbarWithIcon.vue';
import Calendar from '@/components/Trip/Calendar.vue';
import CtaBarBlackSiwan from '@/components/compo/CtaBarBlack-siwan.vue';
import { useAuthStore } from '@/stores/auth';
import axios from 'axios';

// API Base URL
const BASEURL = 'http://localhost:8080';

// 라우터 정보 및 인증 스토어
const route = useRoute();
const authStore = useAuthStore();

// 여행 정보 초기값
const trip = ref({
  id: null,
  describe: ref(null), // 기본 설명 설정
  startPeriod: '0000-00-00', // 기본 날짜 설정
  endPeriod: '0000-00-00', // 기본 날짜 설정
  usedBudget: 0,
  remainingBudget: 0,
});

// 데이터 로드 상태
const isDataLoaded = ref(false);

// 선택된 날짜
const selectedDate = ref(null);

// 오늘 날짜 가져오기
const getTodayDate = () => {
  const today = new Date();
  const year = today.getFullYear();
  const month = String(today.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작
  const day = String(today.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};

// 로그 출력 함수
const logTrip = (trip) => {
  console.log(trip);
};

// 여행 정보를 가져오는 함수
const fetchTripInfo = async () => {
  console.log('tripId : ', route.params.tripId);
  console.log(getTodayDate());
  console.log(authStore.token);

  try {
    console.log(route.params.tripId);
    const response = await axios.get(
      `${BASEURL}/trip/test/${route.params.tripId}`
    );

    console.log('초기 여행정보 불러오기');
    console.log(response);

    if (response.data) {
      Object.assign(trip.value, {
        id: route.params.tripId,
        describe: response.data.description || '여행 설명이 없습니다',
        startPeriod: response.data.startPeriod || '',
        endPeriod: response.data.endPeriod || '',
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
    // 날짜를 yyyy-MM-dd 형식으로 변환 (타임존 영향 제거)
    const formattedDate = `${new Date(date).getFullYear()}-${String(
      new Date(date).getMonth() + 1
    ).padStart(2, '0')}-${String(new Date(date).getDate() + 1).padStart(
      2,
      '0'
    )}`;
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

// 날짜 클릭 이벤트 핸들러
const handleDateClick = async (date) => {
  selectedDate.value = date;
  await fetchTripExpense(date);
};

// 컴포넌트 로드 시 여행 정보 가져오기
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
  border: 1px solid black;
}

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
