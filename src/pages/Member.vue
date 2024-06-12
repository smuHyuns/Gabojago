<template>
  <div class="viewport">
    <Topbar titleText="인원 선택" class="top-bar" />
    <div class="content">
      <div class="spacer"></div>
      <!-- Spacer 추가 -->
      <MemberAdd class="Member-add" />
    </div>
    <div class="spacer"></div>
    <!-- Spacer 추가 -->
    <CtaBar inputname="다음으로" @click="navigateToTravelName" />
  </div>
</template>

<script setup>
import Topbar from '@/components/Topbar.vue';
import MemberAdd from '@/components/MemberAdd.vue';
import CtaBar from '@/components/CtaBar.vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const navigateToTravelName = () => {
  router.push('/TravelName');
};
</script>

<style scoped>
.viewport {
  width: 1080px;
  height: 2340px;
  overflow: hidden; /* Prevent scrolling if content exceeds the fixed size */
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between; /* 요소들을 위아래로 균등하게 분배 */
  margin: 0 auto; /* Center the viewport if there is additional space */
  background-color: #fff; /* Optional: Set a background color */
}

.content {
  flex: 1; /* Topbar와 CtaBar 사이의 공간을 채우기 위해 사용 */
}

.spacer {
  height: 58px; /* Spacer 높이 추가 */
}

/* Ensure that the body and html take up the full height of the viewport */
html,
body {
  height: 100%;
  margin: 0;
  padding: 0;
  overflow: hidden; /* Prevent scrolling on the body */
}
</style>
