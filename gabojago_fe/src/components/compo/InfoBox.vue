<template>
  <div class="box">
    <div class="firstBox">{{ firstInput }}</div>
    <div class="secondBox">{{ secondInput }}</div>
    <div class="thirdBox">
      <span class="content" @click="navigateToGun">{{ thirdInput }}</span>
      <img src="@/assets/chevron-left.png" alt="Icon" />
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';
import { useRouter } from 'vue-router';
const router = useRouter();

const navigateToGun = () => {
  router.push('/gun');
};

const props = defineProps({
  firstInput: String,
  secondInput: String,
  thirdInput: String,
});
</script>

<style scoped>
.box {
  width: 1080px;
  max-height: 700px;
  text-align: center;
  color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 30px;
}

.firstBox {
  width: 484px;
  height: 48px;
  font-weight: 300;
  font-size: 40px;
  line-height: 1.2;
  text-align: center;
}

.secondBox {
  width: 615px;
  height: 97px;
  padding-top: 12px;
  padding-bottom: 37px;
  font-weight: 600;
  font-size: 75px;
  line-height: 1.2;
  text-align: center;
  color: #ffffff;
}
.thirdBox {
  padding-left: 50px;
  padding-right: 24px;
  margin: 0;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 375px;
  height: 115px;
  text-align: center;
  background: #ffffff;
  border-radius: 66px;
  color: #3e444e;
  box-sizing: border-box; /* 패딩과 테두리를 요소 크기에 포함 */
}
.content {
  margin-right: 6px; /* 텍스트 오른쪽 마진 추가 */
  font-size: 40px;
  font-weight: 600;
  line-height: 115px; /* 세로 가운데 정렬을 위해 */
  white-space: nowrap; /* 텍스트가 줄 바꿈되지 않도록 함 */
  overflow: visible; /* 요소를 넘어가는 텍스트를 보이도록 함 */
}

.thirdBox img {
  margin-left: 6px; /* 아이콘 왼쪽 마진 추가 */
  margin: 0; /* 이미지에 마진을 없애줌 */
  width: 69px; /* 아이콘의 너비 */
  height: 69px; /* 아이콘의 높이 */
}
</style>
