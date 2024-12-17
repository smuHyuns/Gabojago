<template>
  <div class="topbarBox">
    <div class="imgbox">
      <img class="icon" src="@/assets/chevron-right.png" @click="goBack" />
      <div class="title">{{ titleText }}</div>
    </div>
    <button class="changeIcon" @click="navigateToDeletePage">
      <img src="@/assets/휴지통.png" alt="Icon" class="icon2" />
    </button>
  </div>
  <div></div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const props = defineProps({
  titleText: {
    type: String,
    required: true,
  },
  tripId: {
    type: Number,
    required: true,
  },
  selectedDate: {
    type: String,
    required: true,
  },
});

function goBack() {
  router.back();
}

function navigateToDeletePage() {
  router.push({
    name: 'Detail_transaction_delete',
    params: {
      tripId: props.tripId,
    },
    query: {
      date: props.selectedDate,
    },
  });
}
</script>

<style scoped>
.topbarBox {
  width: 100%;
  margin-top: 10%;
  padding-top: 19px;
  padding-bottom: 19px;
  padding-left: 50px;
  padding-right: 237px;
  display: flex;
  align-items: center;
}

.imgbox {
  display: flex;
  align-items: center;
  margin-left: 20px; /* 좌측 벽과의 거리 */
  justify-content: space-between;
  margin-left: 20px;

  height: 91px; /* 아이콘과 제목의 높이를 동일하게 설정 (70px * 1.3) */
  /* overflow: hidden; 내용이 넘치는 경우 자르기 */
}

.icon {
  height: 75px;
  object-fit: contain;
  overflow: visible;
}

.title {
  width: 500px;
  color: #353b43;
  font-size: 58px;
  font-weight: 700;
  /* 폰트 높이를 아이콘 높이와 동일하게 설정 */
  word-wrap: break-word;
}

.icon2 {
  width: 69px;
  height: 69px;
  padding: 8px;
  justify-content: flex-end;
  align-items: center;
}
.changeIcon {
  flex-grow: 1;
  background: none;
  border: none; /* 아이콘과 동일한 높이 */
  padding-left: 100px;
}
</style>
