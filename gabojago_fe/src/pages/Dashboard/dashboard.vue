<template>
  <div class="BlueBox">
    <Header class="header" style="z-index: 9999;" />
    <div class="imgContainer">
      <img src="@/assets/비행기토끼.png" class="rabbitImg" />
    </div>
    <InfoBox
      class="infoBox"
      firstInput="돈 관리는 저희가 도와드릴게요"
      secondInput="어디로 떠나시나요?"
      thirdInput="여행 등록하기"
    />
  </div>
  <div class="whiteBox">
    <div class="kindTripBox">
      <ul>
        <li class="tripCategory" :class="{ selected: selectedCategory === '전체' }" @click="selectCategory('전체')">
          <div class="tripCategoryName">전체</div>
          <div class="tripCategoryCount">{{ totalTrips }}</div>
        </li>
        <li class="tripCategory" :class="{ selected: selectedCategory === '여행 중' }" @click="selectCategory('여행 중')">
          <div class="tripCategoryName">여행 중</div>
          <div class="tripCategoryCount">{{ ongoingTrips }}</div>
        </li>
        <li class="tripCategory" :class="{ selected: selectedCategory === '다가오는 여행' }" @click="selectCategory('다가오는 여행')">
          <div class="tripCategoryName">다가오는 여행</div>
          <div class="tripCategoryCount">{{ upcomingTrips }}</div>
        </li>
        <li class="tripCategory" :class="{ selected: selectedCategory === '완료' }" @click="selectCategory('완료')">
          <div class="tripCategoryName">지난 여행</div>
          <div class="tripCategoryCount">{{ completedTrips }}</div>
        </li>
      </ul>
    </div>
    <div class="planListBox">
      <div 
        class="useBox" 
        v-for="(trip, index) in filteredTrips" 
        :key="index"
        :class="{ faded: isFaded(trip) }"
        @click="goToAccountCalendar(trip.id)"
      >
        <img class="useBox-img" src="../assets/프로필비행기토끼.png"></img>
        <div class="useBox-txt">
          <span class="useBox-txt-main">{{ trip.describe }}</span><br />
          <span class="useBox-txt-sub">
            <template v-if="trip.daysUntilTrip > 0">
              {{ trip.country }} · D-{{ trip.daysUntilTrip }}
            </template>
            <template v-else>
              {{ formatPeriod(trip.startPeriod, trip.endPeriod) }}
            </template>
          </span>
        </div>
        <div class="useBox-detail">
          <img src="../assets/userBox-left.png" />
        </div>
      </div>
    </div>
  
  </div>
</template>

<script setup>
import axios from 'axios';
import Header from '@/components/Header.vue';
import InfoBox from '@/components/InfoBox.vue';
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const totalTrips = ref(0);
const ongoingTrips = ref(0);
const upcomingTrips = ref(0);
const completedTrips = ref(0);
const trips = ref([]);
const selectedCategory = ref('전체');
const router = useRouter();

const formatPeriod = (start, end) => {
  const formatDate = (date) => {
    const [year, month, day] = date.split('-');
    return `${year.slice(2)}.${month}.${day}`;
  };
  const startDate = formatDate(start);
  const endDate = formatDate(end);
  return `${startDate} ~ ${endDate}`;
};

const selectCategory = (category) => {
  selectedCategory.value = category;
};

const filteredTrips = computed(() => {
  if (selectedCategory.value === '전체') {
    return trips.value;
  }
  return trips.value.filter(trip => trip.travelComplete === selectedCategory.value);
});

const goToAccountCalendar = (tripId) => {
  router.push({ name: 'Tokyo_calendar', params: { tripId } });
};

const isFaded = (trip) => {
  const currentDate = new Date();
  const startPeriod = new Date(trip.startPeriod);
  const endPeriod = new Date(trip.endPeriod);
  return trip.daysUntilTrip === 0 && !(currentDate >= startPeriod && currentDate <= endPeriod);
};

onMounted(async () => {
  try {
    const response = await fetch('/db.json'); // JSON 파일 경로 확인
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();
    const userTrips = data.users[0].trips;

    trips.value = userTrips;
    totalTrips.value = userTrips.length;
    ongoingTrips.value = userTrips.filter(trip => trip.travelComplete === '여행 중').length;
    upcomingTrips.value = userTrips.filter(trip => trip.travelComplete === '다가오는 여행').length;
    completedTrips.value = userTrips.filter(trip => trip.travelComplete === '완료').length;
  } catch (error) {
    console.error('Error fetching data: ', error);
  }
});
</script>

<style scoped>
.faded {
  opacity: 0.7;
}

.BlueBox {
  height: 1180px;
  width: 1080px;
  margin: 0;
  background-color: var(--blue-200);
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: hidden;
  margin-bottom: 0;
  padding-bottom: 0;
}

.imgContainer {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-grow: 1;
  position: absolute;
}

.header {
  position: relative;
  top: 126px; /* 기존에는 170px이었음 */
}

.rabbitImg {
  width: 604px;
  height: auto;
  object-fit: contain;
  margin-top: 300px; /* 헤더와의 간격을 조정 */
}

.infoBox {
  position: absolute;
  height: 476px;
  bottom: 0; 
  width: 100%; /* infoBox가 BlueBox의 전체 너비를 차지하도록 설정 */
  margin: 0;
  background: linear-gradient(
    180deg,
    rgba(123, 160, 255, 0) 0%,
    #749bff 36%,
    #5d8bff 100%
  );
}

.whiteBox {
  height: 1115px;
  width: 1080px;
  background-color: #fff;
  /* margin: 0; */
  position: relative; /* 버튼 위치 설정을 위한 기준 요소 */
}

/* 카테고리 css start*/

.kindTripBox {
  max-width: 1080px;
  height: 184px;
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 0;
  overflow-x: auto; /* 내용이 넘칠 경우 수평 스크롤 */
  justify-content: flex-start; /* 좌측 정렬 */
  margin: 0 58px;
}

.kindTripBox ul {
  display: flex;
  gap: 20px;
  list-style: none;
  padding: 0;
  margin: 0;
}

.tripCategory {
  height: 94px; 
  padding: 0 34px;
  gap: 15px; 
  display: flex; /* 가로 정렬을 위해 flex로 설정 */
  align-items: center; /* 텍스트를 수직 가운데 정렬 */
  background: #F5F6F7; 
  border-radius: 57px;
  font-size: 40px;
  font-weight: 500;
  color: #8892A0; 
}

.tripCategory:hover {
  background-color: #616B79; 
  color: #FFFFFF !important;
  transition-duration: 0.5s;
}

.tripCategoryName {
  font-weight: 500;
  overflow: hidden; /* 넘치는 텍스트 숨김 */
  text-overflow: ellipsis; /* 넘치는 텍스트를 생략 부호로 표시 */
  color: inherit;
  white-space: nowrap; /* 한 줄로만 표시되도록 설정 */
}

.tripCategoryCount {
  font-weight: 500;
  word-wrap: break-word;
  color: inherit; 
}

.tripCategoryCount {
  font-size: 40px; 
}

.selected {
  background-color: #616B79; 
  color: #FFFFFF !important; 
}

/* 카테고리 css end*/

.planListBox {
  width: 964px;
  max-height: 950px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  align-content: flex-start;
  gap: 35px;
  overflow-x: hidden; /* 수평 스크롤 비활성화 */
  overflow-y: scroll; /* 수직 스크롤 활성화 */
}

.noneBox {
  font-size: 40px;
  text-align: center;
  color: var(--grey-400);
}

.useBox {
  width: 100%;
  height: 259.2px;
  background: #f5f6f7;
  border-radius: 29px;
  margin: 0;
  padding: 0;
  display: flex;
  flex-shrink: 0;
  align-items: center;
  justify-content: space-between;
}

.useBox-txt {
  margin: 0; /* 이전에 있던 여백 제거 */
  padding: 0;
  flex-grow: 1;
}

.useBox-img {
  width: 144px;
  height: 144px;
  object-fit: cover;
  margin-right: 27px; /* 이미지 오른쪽 여백 조정 */
  margin-left: 48px; /* 왼쪽 여백 추가 */    
}


.useBox-detail {
  flex-grow: 0;
  padding-right: 3em
}

.useBox-detail img {
  width: 69px;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  
}

.useBox-txt-main {
  color: #3e444e;
  font-size: 46px;
  font-weight: 500;
  line-height: 46px;
  word-wrap: break-word;
  display: block;
  padding-bottom: 20px; /* 간격 조정 */
}

.useBox-txt-sub {
  color: #8892a0;
  font-size: 34px;
  font-weight: 300;
  line-height: 34px;
  word-wrap: break-word;
  display: block;
}

.addPlanBtn {
  width: 161px;
  height: 161x;
  position: absolute;
  border-radius: 100%;
  right: 59px; /* 오른쪽 50px 위치 */
  bottom: 108px; /* 아래쪽 100px 위치 */
  border: none;
  cursor: pointer;
  box-shadow: 0px 0px 28px rgba(0, 0, 0, 0.15);
}
</style>
