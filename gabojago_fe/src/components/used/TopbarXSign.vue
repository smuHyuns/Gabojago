<template>
  <div class="topbarBox">
    <div class="imgbox">
      <img class="icon" src="@/assets/xsign.png" @click="navigateToDashBoard" />
      <div class="title">{{ titleText }}</div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  titleText: {
    type: String,
    required: true,
  },
});
import { useRouter } from 'vue-router';
const router = useRouter();

const navigateToDashBoard = () => {
  router.push('/dashboard');
};
</script>

<style scoped>
.topbarBox {
  width: 100%;
  padding-top: 19px;
  padding-bottom: 19px;
  padding-left: 50px;
  padding-right: 237px;
  display: flex;
  align-items: center; /* 수직 정렬 */
}

.imgbox {
  display: flex;
  align-items: center;
  gap: 10px; /* 아이콘과 제목 사이의 거리 */
  margin-left: 20px; /* 좌측 벽과의 거리 */
  height: 91px; /* 아이콘과 제목의 높이를 동일하게 설정 (70px * 1.3) */
  overflow: hidden; /* 내용이 넘치는 경우 자르기 */
}

.icon {
  height: 75px; /* 아이콘의 높이를 1.3배 증가 (70px * 1.3) */
  object-fit: contain;
  overflow: visible;
  cursor: pointer;
}

.title {
  color: #353b43;
  font-size: 58px; /* 폰트 크기를 1.3배 증가 (24px * 1.3) */
  font-weight: 700;
  line-height: 91px; /* 폰트 높이를 아이콘 높이와 동일하게 설정 */
  word-wrap: break-word;
}
</style>
