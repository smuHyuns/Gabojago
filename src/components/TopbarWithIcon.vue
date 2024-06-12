<template>
  <div class="topbarBox">
    <div class="imgbox">
      <img class="icon" src="../assets/xsign.png" />
      <div class="title">{{ titleText }}</div>
      <button class="Changeicon">
        <img src="../assets/xsign.png" alt="Icon" class="icon2" />
      </button>
      <!-- ChangeIcon은 변경하세요~~ -->
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
.topbarBox {
  margin-top: 10%;
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
  height: 91px; /* 아이콘과 제목의 높이를 동일하게 설정 (70px * 1.3)  /* 내용이 넘치는 경우 자르기 */
}

.icon {
  height: 75px; /* 아이콘의 높이를 1.3배 증가 (70px * 1.3) */
  object-fit: contain;
  overflow: visible;
}

.title {
  color: #353b43;
  font-size: 58px; /* 폰트 크기를 1.3배 증가 (24px * 1.3) */
  font-weight: 700;
  line-height: 91px; /* 폰트 높이를 아이콘 높이와 동일하게 설정 */
  word-wrap: break-word;
}
.icon2 {
  width: 100px;
  height: 100px; /* 여기서 아이콘 크기 변경하세요~~~*/
}
.Changeicon {
  position: relative;
  display: flex;
  left: 520px; /* 여기서 left로 부터 멀어지는 값 볼 수 잇어요~~ */
  background: none;
  border: none; /* 아이콘과 동일한 높이 */
}
</style>
