<template>
  <div class="topbarBox">
    <div class="imgbox">
      <img class="icon" src="../assets/chevron-right.png" />
      <div class="title">{{ titleText }}</div>
      <img class="icon" src="../assets/chevron-left.png" />
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
</script>

<style scoped>
body {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  margin: 0;
}

.topbarBox {
  width: 100%;
  max-width: 1080px;
  padding-top: 19px;
  padding-bottom: 19px;
  display: flex;
  justify-content: center;
  align-items: center; /* 수직 정렬 */
}

.imgbox {
  display: flex;
  align-items: center;
  gap: 40px; /* 아이콘과 제목 사이의 거리 */
  height: 91px; /* 아이콘과 제목의 높이를 동일하게 설정 (70px * 1.3) */
}

.icon {
  width: 31.2px; /* 아이콘의 너비를 1.3배 증가 (24px * 1.3) */
  height: 91px; /* 아이콘의 높이를 1.3배 증가 (70px * 1.3) */
  object-fit: cover;
  filter: invert(91%) sepia(7%) saturate(100%) hue-rotate(180deg) brightness(115%) contrast(80%);
}

.title {
  color: #353b43;
  font-size: 50px; /* 폰트 크기를 1.3배 증가 (24px * 1.3) */
  font-family: Pretendard;
  font-weight: 700;
  line-height: 91px; /* 폰트 높이를 아이콘 높이와 동일하게 설정 */
  padding-left: 60px;
  padding-right: 60px;
  word-wrap: break-word;
}
</style>
