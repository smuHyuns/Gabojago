<template>
  <div class="BlueBox">
    <Header class="header" style="z-index: 9999" />
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
        <li
          class="tripCategory"
          :class="{ selected: selectedCategory === '전체' }"
          @click="selectCategory('전체')"
        >
          <div class="tripCategoryName">전체</div>
          <div class="tripCategoryCount">{{ totalTrips }}</div>
        </li>
        <li
          class="tripCategory"
          :class="{ selected: selectedCategory === '여행 중' }"
          @click="selectCategory('여행 중')"
        >
          <div class="tripCategoryName">여행 중</div>
          <div class="tripCategoryCount">{{ ongoingTrips }}</div>
        </li>
        <li
          class="tripCategory"
          :class="{ selected: selectedCategory === '다가오는 여행' }"
          @click="selectCategory('다가오는 여행')"
        >
          <div class="tripCategoryName">다가오는 여행</div>
          <div class="tripCategoryCount">{{ upcomingTrips }}</div>
        </li>
        <li
          class="tripCategory"
          :class="{ selected: selectedCategory === '완료' }"
          @click="selectCategory('완료')"
        >
          <div class="tripCategoryName">지난 여행</div>
          <div class="tripCategoryCount">{{ completedTrips }}</div>
        </li>
      </ul>
    </div>
    <div class="planListBox">
      <div
        class="useBox"
        v-for="(trip, index) in filteredTrips"
        :key="trip.tripId"
        :class="{ faded: isFaded(trip) }"
        @click="goToAccountCalendar(trip.tripId)"
      >
        <img class="useBox-img" src="@/assets/프로필비행기토끼.png" />
        <div class="useBox-txt">
          <span class="useBox-txt-main">{{ trip.description }}</span
          ><br />
          <span class="useBox-txt-sub">
            <template v-if="trip.tripStatus === 0">
              {{ trip.tripCountry }} · D-{{
                calculateDaysUntilTrip(trip.startPeriod)
              }}
            </template>
            <template v-else>
              {{ formatPeriod(trip.startPeriod, trip.endPeriod) }}
            </template>
          </span>
        </div>
        <div class="useBox-detail">
          <img src="@/assets/userBox-left.png" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from '@/api/axios';
import Header from '@/components/used/Header.vue';
import InfoBox from '@/components/used/InfoBox.vue';
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import { getAllTrips, getTripStatus } from '@/api/trip';

const totalTrips = ref(0);
const ongoingTrips = ref(0);
const upcomingTrips = ref(0);
const completedTrips = ref(0);
const trips = ref([]);
const selectedCategory = ref('전체');
const router = useRouter();
const authStore = useAuthStore();

const formatPeriod = (start, end) => {
  const formatDate = (date) => {
    const [year, month, day] = date.split('-');
    return `${year.slice(2)}.${month}.${day}`;
  };
  return `${formatDate(start)} ~ ${formatDate(end)}`;
};

const calculateDaysUntilTrip = (startDate) => {
  const today = new Date();
  const start = new Date(startDate);
  const diff = Math.ceil((start - today) / (1000 * 60 * 60 * 24));
  return diff > 0 ? diff : 0;
};

const selectCategory = async (category) => {
  selectedCategory.value = category;
  await fetchTripsByCategory();
};

const filteredTrips = computed(() => {
  if (selectedCategory.value === '전체') return trips.value;
  return trips.value.filter((trip) => {
    if (selectedCategory.value === '다가오는 여행')
      return trip.tripStatus === 0;
    if (selectedCategory.value === '여행 중') return trip.tripStatus === 1;
    if (selectedCategory.value === '완료') return trip.tripStatus === 2;
    return false;
  });
});

const goToAccountCalendar = (tripId) => {
  if (!tripId) {
    console.error('Invalid tripId:', tripId);
    return;
  }
  router.push({ name: 'Trip_Detail', params: { tripId } });
};

const isFaded = (trip) => {
  const currentDate = new Date();
  const startPeriod = new Date(trip.startPeriod);
  const endPeriod = new Date(trip.endPeriod);
  if (currentDate >= startPeriod && currentDate <= endPeriod) return false;
  if (currentDate <= startPeriod && currentDate <= startPeriod) return false;
  return true;
};

const fetchAllTrips = async () => {
  try {
    const response = await getAllTrips();
    trips.value = response.data;
    totalTrips.value = trips.value.length;
    ongoingTrips.value = trips.value.filter(
      (trip) => trip.tripStatus === 1
    ).length;
    upcomingTrips.value = trips.value.filter(
      (trip) => trip.tripStatus === 0
    ).length;
    completedTrips.value = trips.value.filter(
      (trip) => trip.tripStatus === 2
    ).length;
  } catch (error) {
    console.error('Failed to fetch trips:', error);
  }
};

const fetchTripsByCategory = async () => {
  if (selectedCategory.value === '전체') {
    await fetchAllTrips();
    return;
  }

  let status = null;
  if (selectedCategory.value === '다가오는 여행') status = 0;
  if (selectedCategory.value === '여행 중') status = 1;
  if (selectedCategory.value === '완료') status = 2;

  try {
    const response = await getTripStatus(status);
    trips.value = response.data;
  } catch (error) {
    console.error('category 패치 실패 :', error);
  }
};

onMounted(async () => {
  if (!authStore.token) {
    router.push('/login');
    return;
  }
  await fetchAllTrips();
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
  top: 126px;
}

.rabbitImg {
  width: 604px;
  height: auto;
  object-fit: contain;
  margin-top: 300px;
}

.infoBox {
  position: absolute;
  height: 476px;
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
  height: 1115px;
  width: 1080px;
  background-color: #fff;
  position: relative;
}

.kindTripBox {
  max-width: 1080px;
  height: 184px;
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 0;
  overflow-x: auto;
  justify-content: flex-start;
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
  display: flex;
  align-items: center;
  background: #f5f6f7;
  border-radius: 57px;
  font-size: 40px;
  font-weight: 500;
  color: #8892a0;
}

.tripCategory:hover {
  background-color: #616b79;
  color: #ffffff !important;
  transition-duration: 0.5s;
}

.tripCategoryName {
  font-weight: 500;
  overflow: hidden;
  text-overflow: ellipsis;
  color: inherit;
  white-space: nowrap;
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
  background-color: #616b79;
  color: #ffffff !important;
}

.planListBox {
  width: 964px;
  max-height: 950px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  align-content: flex-start;
  gap: 35px;
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
  border-radius: 29px;
  margin: 0;
  padding: 0;
  display: flex;
  flex-shrink: 0;
  align-items: center;
  justify-content: space-between;
}

.useBox-txt {
  margin: 0;
  padding: 0;
  flex-grow: 1;
}

.useBox-img {
  width: 144px;
  height: 144px;
  object-fit: cover;
  margin-right: 27px;
  margin-left: 48px;
}

.useBox-detail {
  flex-grow: 0;
  padding-right: 3em;
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
  padding-bottom: 20px;
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
  right: 59px;
  bottom: 108px;
  border: none;
  cursor: pointer;
  box-shadow: 0px 0px 28px rgba(0, 0, 0, 0.15);
}
</style>
