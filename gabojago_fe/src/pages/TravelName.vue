<template>
  <div class="viewport">
    <Topbar titleText="여행 제목" />
    <TextInput2
      placeholder="제목을 입력해주세요"
      v-model="travelTitle"
      @input="limitInput"
    />
    <div class="spacer"></div>
    <CtaBar
      class="bottom-bar"
      inputname="여행 등록하기"
      :on="isblack"
      @submit="updateNickname"
      @click="navigateToAddPayment"
    />
  </div>
</template>

<script setup>
import Topbar from '@/components/Topbar.vue';
import TextInput2 from '@/components/TextInput2.vue';
import CtaBar from '@/components/CtaBar.vue';
import { useRouter, useRoute } from 'vue-router';
import { ref } from 'vue';

const router = useRouter();
const route = useRoute();
const travelTitle = ref('');
const selectedCountries = ref(
  route.query.countries ? route.query.countries.split(',') : []
);
const selectedDates = ref(
  route.query.selectedDates ? JSON.parse(route.query.selectedDates) : []
);
const memberCount = ref(
  route.query.memberCount ? parseInt(route.query.memberCount) : 0
);

const isblack = ref(false);

const limitInput = () => {
  if (travelTitle.value.length > 10) {
    travelTitle.value = travelTitle.value.slice(0, 10);
  }
  isblack.value = travelTitle.value.length >= 1;
};

const navigateToAddPayment = () => {
  router.push({
    path: '/addPayment',
    query: {
      countries: selectedCountries.value.join(','),
      selectedDates: JSON.stringify(selectedDates.value),
      memberCount: memberCount.value,
      travelTitle: travelTitle.value,
    },
  });
};
</script>

<style scoped>
.viewport {
  width: 1080px;
  height: 2340px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  margin: 0 auto;
  background-color: #fff;
}

.spacer {
  flex-grow: 1;
}

.bottom-bar {
  margin-top: 30px;
}
</style>
