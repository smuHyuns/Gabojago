<template>
  <div class="viewport">
    <Topbar titleText="여행 제목" />
    <div class="content">
      <InputBox v-model="travelTitle" TextInput="제목을 입력해 주세요" />
    </div>
    <CtaBar inputname="여행 등록하기" @click="navigateToAddPayment" />
  </div>
</template>

<script setup>
import Topbar from '@/components/Topbar.vue';
import InputBox from '@/components/InputBox.vue';
import CtaBar from '@/components/CtaBar.vue';
import { useRouter, useRoute } from 'vue-router';
import { ref } from 'vue';

const router = useRouter();
const route = useRoute();

const travelTitle = ref('');
const selectedCountries = ref(route.query.countries ? route.query.countries.split(',') : []);
const selectedDates = ref(route.query.selectedDates ? JSON.parse(route.query.selectedDates) : []);
const memberCount = ref(route.query.memberCount ? parseInt(route.query.memberCount) : 0);

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
  overflow: hidden;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  margin: 0 auto;
  background-color: #fff;
}

.content {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

html,
body {
  height: 100%;
  margin: 0;
  padding: 0;
  overflow: hidden;
}
</style>
