<template>
  <div class="viewport">
    <Topbar titleText="인원 선택" class="top-bar" />
    <div class="content">
      <div class="spacer"></div>
      <MemberAdd class="Member-add" @update:memberCount="updateMemberCount" />
    </div>
    <div class="spacer"></div>
    <!-- <CtaBar inputname="다음으로" @click="navigateToTravelName" /> -->
    <CtaBarBlack inputname="다음으로" @click="navigateToTravelName" />
  </div>
</template>

<script setup>
import Topbar from '@/components/Topbar.vue';
import MemberAdd from '@/components/MemberAdd.vue';
// import CtaBar from '@/components/CtaBar.vue';
import CtaBarBlack from '@/components/CtaBarBlack.vue';

import { useRouter, useRoute } from 'vue-router';
import { ref } from 'vue';

const router = useRouter();
const route = useRoute();

const selectedCountries = ref(route.query.countries ? route.query.countries.split(',') : []);
const selectedDates = ref(route.query.selectedDates ? JSON.parse(route.query.selectedDates) : []);
const memberCount = ref(0);

const updateMemberCount = (count) => {
  memberCount.value = count;
};

const navigateToTravelName = () => {
  router.push({
    path: '/TravelName',
    query: {
      countries: selectedCountries.value.join(','),
      selectedDates: JSON.stringify(selectedDates.value),
      memberCount: memberCount.value,
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
}

.spacer {
  height: 58px;
}

html,
body {
  height: 100%;
  margin: 0;
  padding: 0;
  overflow: hidden;
}
</style>
