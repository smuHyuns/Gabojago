<template>
  <div>
    <Topbar titleText="여행지 제목" class="topbar" />
    <div class="calendar-box">
      <Cal @dateSelected="handleDateSelected" />
    </div>
    <CtaBar
      class="ctabar"
      inputname="다음으로"
      :on="isblack"
      @click="navigateToMember"
      :style="{ cursor: isblack ? 'pointer' : 'auto' }"
    />
  </div>
</template>

<script setup>
import Topbar from '@/components/compo/Topbar.vue';
import Cal from '@/components/compo/Cal.vue';
import CtaBar from '@/components/compo/CtaBar.vue';
import { useRouter, useRoute } from 'vue-router';
import { ref } from 'vue';

const router = useRouter();
const route = useRoute();

const selectedDates = ref([]);
const isblack = ref(false);
const selectedCountries = ref(
  route.query.countries ? route.query.countries.split(',') : []
);

const handleDateSelected = (dates) => {
  isblack.value = dates.length > 0;
  selectedDates.value = dates;
};

const navigateToMember = () => {
  router.push({
    path: '/Member',
    query: {
      selectedDates: JSON.stringify(selectedDates.value),
      countries: selectedCountries.value.join(','),
    },
  });
};
</script>

<style scoped>
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
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  box-sizing: border-box; /* padding 포함한 크기 계산 */
  aspect-ratio: 1 / 1; /* 1:1 비율을 유지 */
}
</style>
