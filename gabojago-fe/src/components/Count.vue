<template>
  <div>
    <!-- 이 부분은 이전 코드와 동일합니다. -->

    <div class="action-bar">
      <div class="logo-container">
        <img src="../assets/가계부.png" alt="가계부" />
      </div>
      <div class="logo-container">
        <img src="../assets/리포트.png" alt="리포트" />
      </div>
    </div>
  </div>
</template>

<style>
/* 이전 코드와 동일 */
.action-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #f8f9fa;

  width: 1080px;
  height: 200px;

  background: #ffffff;

  border: 1px solid #e1e1e1;

  box-shadow: 0px 0px 57px rgba(62, 68, 78, 0.1);
}

.action-bar img {
  max-height: 80px; /* 아이콘 크기 조정 */
  margin-bottom: 35px; /* 위쪽 여백 조정 */
}

/* 수정된 부분 */
.logo-container {
  flex: 1; /* 좌우로 균일하게 확장 */
  display: flex;
  justify-content: center;
  align-items: center;
}

/* 마우스 호버시 스타일 변경 */
.logo-container:hover img {
  filter: brightness(0) invert(0); /* 아이콘 색상 변경 */
  color: #3e444e;
}
</style>
