<template>
  <div class="BlueBox">
    <Header class="header" />
    <div class="imgContainer">
      <img src="../assets/비행기토끼.png" class="rabbitImg" />
    </div>
    <InfoBox
      class="infoBox"
      firstInput="돈 관리는 저희가 도와드릴게요"
      secondInput="어디로 떠나시나요?"
      thirdInput="여행 등록하기 >"
    />
  </div>
  <div class="whiteBox">
    <div class="kindTripBox">
      <ul>
        <li class="tripCategory selected">
          <div class="tripCategoryName">전체</div>
          <div class="tripCategoryCount">{{ totalTrips }}</div>
        </li>
        <li class="tripCategory">
          <div class="tripCategoryName">여행 중</div>
          <div class="tripCategoryCount">{{ ongoingTrips }}</div>
        </li>
        <li class="tripCategory">
          <div class="tripCategoryName">다가오는 여행</div>
          <div class="tripCategoryCount">{{ upcomingTrips }}</div>
        </li>
        <li class="tripCategory">
          <div class="tripCategoryName">지난 여행</div>
          <div class="tripCategoryCount">{{ pastTrips }}</div>
        </li>
      </ul>
    </div>
    <div class="planListBox">
      <div class="useBox" v-for="trip in trips" :key="trip.startPeriod">
        <img class="useBox-img" src="../assets/비행기토끼.png"></img>
        <div class="useBox-txt">
          <span class="useBox-txt-main">{{ trip.description }}</span><br />
          <span class="useBox-txt-sub">D-{{ trip.daysUntilTrip }}</span>
        </div>
        <div class="useBox-detail">
          <img src="../assets/chevron-left.png" />
        </div>
      </div>
    </div>
    <img src="../assets/addPlanBtn.png" class="addPlanBtn" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import Header from '@/components/Header.vue';
import InfoBox from '@/components/InfoBox.vue';

// 상태 관리 변수
const totalTrips = ref(0);
const ongoingTrips = ref(0);
const upcomingTrips = ref(0);
const pastTrips = ref(0);
const trips = ref([]);

// 데이터 가져오기 및 처리 함수
const fetchData = async () => {
  try {
    const response = await axios.get('/path/to/db.json');
    const data = response.data;

    let total = 0;
    let ongoing = 0;
    let upcoming = 0;
    let past = 0;
    const today = new Date();

    data.users.forEach(user => {
      user.trips.forEach(trip => {
        total++;
        const startDate = new Date(trip.startPeriod);
        const endDate = new Date(trip.endPeriod);

        if (startDate <= today && endDate >= today) {
          ongoing++;
        } else if (startDate > today) {
          upcoming++;
        } else if (endDate < today) {
          past++;
        }

        // trips 배열에 추가
        trips.value.push({
          description: trip.description,
          daysUntilTrip: trip.daysUntilTrip,
        });
      });
    });

    totalTrips.value = total;
    ongoingTrips.value = ongoing;
    upcomingTrips.value = upcoming;
    pastTrips.value = past;
  } catch (error) {
    console.error('데이터를 가져오는 중 오류 발생:', error);
  }
};

onMounted(fetchData);
</script>

<style scoped>
.BlueBox {
  height: 1170px;
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
  position: relative;
}

.rabbitImg {
  max-width: 100%;
  max-height: calc(100% - 220px);
  width: auto;
  height: auto;
  object-fit: contain;
  transform: scale(1.1);
  margin-top: 20px;
}

.header {
  position: relative;
  top: 170px;
}

.infoBox {
  position: absolute;
  height: 430px;
  bottom: 0;
  width: 100%;
  margin: 0;
  background: linear-gradient(
    180deg,
    rgba(123, 160, 255, 0) 0%,
    #749bff 36%,
    #5d8bff 100%
  );
}

.whiteBox {
  height: 1160px;
  width: 1080px;
  background-color: var(--grey-0);
  margin: 0;
  position: relative;
}

.kindTripBox {
  max-width: 1080px;
  height: 100px;
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px;
  overflow-x: auto;
  justify-content: flex-start;
  margin: 30px;
}

.kindTripBox ul {
  display: flex;
  gap: 20px;
  list-style: none;
  padding: 0;
  margin: 0;
}

.tripCategory {
  height: 60px;
  justify-content: center;
  align-items: center;
  gap: 15px;
  display: inline-flex;
  background: var(--grey-200);
  border-radius: 40px;
  padding: 20px 25px;
  font-size: 30px;
  color: var(--grey-700);
}

.tripCategory:hover {
  background-color: var(--grey-700);
  color: white !important;
  transition-duration: 0.8s;
}

.tripCategoryName,
.tripCategoryCount {
  font-weight: 600;
  word-wrap: break-word;
  color: inherit;
}

.tripCategoryCount {
  font-size: 22px;
}

.selected {
  background-color: var(--grey-700);
  color: white !important;
}

.planListBox {
  width: 900px;
  max-height: 950px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  align-content: flex-start;
  gap: 20px;
  overflow-x: hidden;
  overflow-y: scroll;
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
  border-radius: 28.8px;
  margin: 0;
  padding: 0;
  display: flex;
  flex-shrink: 0;
  align-items: center;
  justify-content: space-between;
  gap: 100px;
}

.useBox-txt {
  margin: 0;
  padding: 0;
}

.useBox-img {
  width: 144px;
  height: 144px;
  object-fit: cover;
  background: #d9d9d9;
  border-radius: 9999px;
}

.useBox-txt-main {
  color: #3e444e;
  font-size: 46.08px;
  font-weight: 600;
  line-height: 46.08px;
  word-wrap: break-word;
  padding: 10px;
}

.useBox-txt-sub {
  color: #8892a0;
  font-size: 34.56px;
  font-weight: 400;
  line-height: 34.56px;
  word-wrap: break-word;
  padding: 10px;
}

.addPlanBtn {
  width: 200px;
  height: 200px;
  position: absolute;
  right: 50px;
  bottom: 100px;
  border: none;
  cursor: pointer;
}
</style>
