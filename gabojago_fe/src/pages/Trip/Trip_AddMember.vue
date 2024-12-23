<template>
  <div class="viewport">
    <Topbar titleText="인원 선택" class="top-bar" />
    <div class="content">
      <div class="spacer"></div>
      <MemberAdd class="Member-add" @update:memberCount="updateMemberCount" />
    </div>
    <div class="spacer"></div>
    <CtaBarBlack inputname="다음으로" @click="navigateToTravelName" />
  </div>
</template>

<script setup>
import Topbar from '@/components/compo/Topbar.vue';
import MemberAdd from '@/components/compo/MemberAdd.vue';
import CtaBarBlack from '@/components/compo/CtaBarBlack.vue';

import { useRouter, useRoute } from 'vue-router';
import { onMounted, ref } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { useAddTripStore } from '@/stores/tripStore';

const authStore = useAuthStore();
const tripStore = useAddTripStore();
const router = useRouter();
const route = useRoute();

const memberCount = ref(1);

const updateMemberCount = (count) => {
  memberCount.value = count;
};

const navigateToTravelName = () => {
  tripStore.setHeadcount(memberCount.value);
  console.log(memberCount.value);
  router.push({
    path: '/add-description',
  });
};

onMounted(async () => {
  if (!authStore.token) {
    router.push('/login');
    return;
  }
  tripStore.setHeadcount(1);
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
