<template>
  <div class="viewport">
    <div class="img">
      <img src="../assets/토끼울음.png" />
    </div>
    <div class="textbox">
      <div class="error-message">앗! 오류가 발생했어요..</div>
      <div class="retry-message">잠시 후에 다시 시도해주세요</div>
    </div>
    <div class="spacer"></div>
    <!-- Spacer added here -->
    <CtaBarBlack inputname="재시도" class="bottom-bar" />
  </div>
</template>

<script setup>
import CtaBarBlack from '@/components/compo/CtaBarBlack.vue';
</script>

<style scoped>
.viewport {
  width: 1080px;
  height: 2340px;
  overflow: hidden; /* 콘텐츠가 고정된 크기를 초과할 경우 스크롤을 방지 */
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start; /* 상단에 배치하기 위해 flex-start로 설정 */
  margin: 0 auto; /* 여유 공간이 있을 경우 뷰포트를 중앙에 배치 */
  background-color: #fff; /* 선택 사항: 배경색 설정 */
  position: relative; /* 자식 요소의 위치를 상대적으로 설정하기 위한 컨텍스트 확보 */
}

.textbox {
  text-align: center;
}

.error-message {
  font-weight: 700; /* 두꺼운 글꼴 설정 */
  font-size: 75px;
  line-height: 1.4;
  text-align: center;
  color: #353b43;
}

.retry-message {
  font-weight: 400;
  font-size: 52px;
  line-height: 1.5;
  text-align: center;
  color: #8892a0;
  margin-top: 16px; /* 텍스트 사이의 여백 설정 */
}

.img img {
  margin-top: 443px; /* 상단에서 443px 떨어지도록 설정 */
  margin-bottom: 20px; /* 텍스트와 버튼 사이에 여백 추가 */
  max-width: 100%;
}

.bottom-bar {
  margin-bottom: 133px; /* 하단에서 약간의 공간 추가 */
}
</style>
