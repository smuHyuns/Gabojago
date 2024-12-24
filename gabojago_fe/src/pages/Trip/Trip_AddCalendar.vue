<template>
  <div class="viewport">
    <div>
      <Topbar titleText="날짜 선택" class="topbar" />
      <div class="calendar-box">
        <Calendar @dateSelected="handleDateSelected" />
      </div>
      <CtaBar
        class="ctabar"
        inputname="다음으로"
        :on="isblack"
        @click="navigateToMember"
        :style="{ cursor: isblack ? 'pointer' : 'auto' }"
      />
    </div>
  </div>
</template>

<script setup>
import Topbar from '@/components/used/Topbar.vue';
import Calendar from '@/components/used/Calendar.vue';
import CtaBar from '@/components/used/CtaBar.vue';
import { useRouter, useRoute } from 'vue-router';
import { onMounted, ref } from 'vue';
import { useAddTripStore } from '@/stores/tripStore';
import { useAuthStore } from '@/stores/auth';

const authStore = useAuthStore();
const tripStore = useAddTripStore();
const router = useRouter();
const route = useRoute();

const selectedDates = ref([]);
const isblack = ref(false);

// const selectedCountries = ref(
//   route.query.countries ? route.query.countries.split(',') : []
// );

const handleDateSelected = (dates) => {
  isblack.value = dates.length > 0;
  selectedDates.value = dates;
};

const navigateToMember = () => {
  if (selectedDates.value.length >= 2) {
    tripStore.setStartPeriod(selectedDates.value[0]);
    tripStore.setEndPeriod(selectedDates.value[1]);
    router.push({
      path: '/add-member',
    });
  } else {
    console.error('날짜를 지정하지 않음');
  }
};

onMounted(async () => {
  if (!authStore.token) {
    router.push('/login');
    return;
  }
  tripStore.setStartPeriod(null);
  tripStore.setEndPeriod(null);
});
</script>

<style scoped>
.viewport {
  width: 1080px;
  height: 2340px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 0 auto;
  background-color: #fff;
  position: relative;
  border: 1px solid black;
}

.ctabar {
  text-align: center;
  margin-top: 740px;
  cursor: pointer;
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
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px;
  margin-left: 40px;
  box-sizing: border-box; /* padding 포함한 크기 계산 */
  aspect-ratio: 1 / 1; /* 1:1 비율을 유지 */
}
</style>
