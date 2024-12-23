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
import Topbar from '@/components/compo/Topbar.vue';
import TextInput2 from '@/components/compo/TextInput2.vue';
import CtaBar from '@/components/compo/CtaBar.vue';
import { useRouter, useRoute } from 'vue-router';
import { onMounted, ref } from 'vue';
import { useAddTripStore } from '@/stores/tripStore';
import { useAuthStore } from '@/stores/auth';
import { saveTrip } from '@/api/trip';

const tripStore = useAddTripStore();
const authStore = useAuthStore();
const router = useRouter();
const route = useRoute();
const travelTitle = ref('');

const isblack = ref(false);

const limitInput = () => {
  if (travelTitle.value.length > 10) {
    travelTitle.value = travelTitle.value.slice(0, 10);
  }
  isblack.value = travelTitle.value.length >= 1;
};

const navigateToAddPayment = async () => {
  tripStore.setDescription(travelTitle.value);

  const request = {
    country: tripStore.country,
    headcount: tripStore.headcount,
    start_period: tripStore.start_period,
    end_period: tripStore.end_period,
    description: tripStore.description,
  };

  try {
    const response = await saveTrip(request);
    tripStore.setTripId(response.tripId);
    router.push({
      path: '/add-payment',
    });
  } catch (error) {
    console.error('여행 저장 오류 발생 :', error);
  }
};

onMounted(async () => {
  if (!authStore.token) {
    router.push('/login');
    return;
  }
  tripStore.setDescription(null);
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

.spacer {
  flex-grow: 1;
}

.bottom-bar {
  margin-top: 30px;
}
</style>
