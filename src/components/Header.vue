<template>
  <div class="header">
    <img class="logoBox" src="../assets/logo.png" />
    <div class="icon-container">
      <img class="infoIcon" src="../assets/userInfoIcon.png" />
    </div>
  </div>
</template>

<script setup></script>

<style scoped>
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding: 0;
  font-size: 58px;
}

.logoBox {
  max-width: 240px;
  max-height: 140px;
  margin-left: 75px;
  transform: scale(1.1);
  transform-origin: center left; /* 확장 중심을 왼쪽으로 설정 */
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
}
</style>
