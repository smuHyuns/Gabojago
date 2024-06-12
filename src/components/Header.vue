<template>
  <div class="header">
    <img class="logoBox" src="../assets/logo.png" />
    <div class="icon-container">
      <img class="infoIcon" src="../assets/userInfoIcon.png" />
    </div>
  </div>
</template>

<script setup>
// 스크립트 블록 내용이 필요한 경우 작성합니다.
</script>

<style scoped>
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 1080px; /* 전체 너비를 1080px로 지정 */
  height: 184px; /* 전체 높이를 184px로 지정 */
  padding: 0;
}

.logoBox {
  width: 243px; /* 가로를 243px로 설정 */
  max-height: 140px;
  margin-left: 75px;
}

.icon-container {
  display: flex;
  justify-content: flex-start; /* 왼쪽 정렬로 변경 */
  flex-grow: 1;
}

.infoIcon {
  width: 69px; /* 가로를 69px로 설정 */
  height: 70px;
  object-fit: cover;
  margin-right: 75px; /* 오른쪽 마진을 추가하여 오른쪽으로 밀어냄 */
}
</style>
