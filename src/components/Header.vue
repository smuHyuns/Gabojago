<template>
  <div class="header">
    <img class="logoBox" src="../assets/logo.png" @click="navigateToHyunsoo" />
    <div class="icon-container">
      <img
        class="infoIcon"
        src="../assets/userInfoIcon.png"
        @click="navigateToIsu"
      />
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const navigateToIsu = () => {
  router.push('/isu');
};

const navigateToHyunsoo = () => {
  router.push('/hyunsoo');
};
</script>
<style scoped>
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding: 0;
}

.logoBox {
  max-width: 240px;
  max-height: 140px;
  margin-left: 75px;
  transform: scale(1.1);
  transform-origin: center left; /* 확장 중심을 왼쪽으로 설정 */
  cursor: pointer;
}

.icon-container {
  display: flex;
  justify-content: flex-end;
  flex-grow: 1;
}

.infoIcon {
  margin-right: 75px;
  height: 70px;
  object-fit: cover;
  cursor: pointer; /* 아이콘에 커서 포인터 추가 */
}
</style>
