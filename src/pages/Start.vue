<template>
  <div class="viewport">
    <div class="textbox">
      <div class="start-msg">
        반가워요! 간편하게 <br />
        돈 관리를 시작해볼까요?
      </div>
      <div class="start-sub-msg">여행에만 집중할 수 있도록 도와드릴게요</div>
    </div>
    <video class="video" autoplay muted playsinline loop>
      <source src="../assets/slide_final.webm" type="video/webm" />
    </video>
    <CtaBarBlack inputname="다음으로" class="bottom-bar" />
  </div>
</template>
<script setup>
import CtaBarBlack from '@/components/CtaBarBlack.vue';
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
.start-msg {
  padding-top: 400px;
  font-weight: bold; /* 두꺼운 글꼴 설정 */
  font-size: 80px;
  line-height: 1.4;
  text-align: center;
  color: #353b43;
}
.start-sub-msg {
  padding-bottom: 280px;
  font-weight: 400;
  font-size: 52px;
  line-height: 1.5;
  text-align: center;
  color: #8892a0;
  margin-top: 16px; /* 텍스트 사이의 여백 설정 */
}
.spacer {
  flex-grow: 1; /* Spacer element to push the button to the bottom */
}

.bottom-bar {
  margin-bottom: 133px; /* 하단에서 약간의 공간 추가 */
}
</style>
