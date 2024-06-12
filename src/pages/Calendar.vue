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
import Topbar from '@/components/Topbar.vue';
import Cal from '@/components/Cal.vue';
import CtaBar from '@/components/CtaBar.vue';
import { useRouter } from 'vue-router';
import { ref } from 'vue';

const router = useRouter();

const navigateToMember = () => {
  router.push({
    path: '/Member',
    query: { selectedDates: selectedDates.value },
  });
};

const isblack = ref(false); // isblack 상태를 저장할 변수
const selectedDates = ref([]);
// Cal 컴포넌트에서 선택한 날짜를 수신하는 이벤트 핸들러
const handleDateSelected = (dates) => {
  // 선택한 날짜가 있는지 확인하고 isblack 상태를 업데이트합니다.
  isblack.value = dates.length > 0;
  selectedDates.value = dates;
};

// 반응형 변수 선언
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
