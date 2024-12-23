<template>
  <div class="topbarBox">
    <div class="imgbox">
      <img class="icon" src="@/assets/chevron-right.png" @click="goBack" />
      <div class="title">{{ titleText }}</div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const props = defineProps({
  titleText: {
    type: String,
    required: true,
  },
});

const router = useRouter();
const goBack = () => {
  router.back();
};
</script>

<style scoped>
.topbarBox {
  width: 1080px;
  height: 184px;
  padding: 0 40px;
  display: flex;
  align-items: center; /* 수직 정렬 */
  margin-top: 127px; /* 항상 top 부분을 127px 띄움 */
}

.imgbox {
  display: flex;
  align-items: center;
  gap: 11px; /* 아이콘과 타이틀 간격 조정 */
  margin-left: 0; /* 좌측 벽과의 거리 */
}

.icon {
  width: 69px;
  height: 69px;
  cursor: pointer;
}

.title {
  height: 75px;

  font-family: Pretendard;
  font-style: normal;
  font-weight: 600;
  font-size: 58px;
  line-height: 1.3;
  letter-spacing: -0.02em;
  text-align: left;

  color: #353b43;
}
</style>
