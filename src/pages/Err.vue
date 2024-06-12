<template>
  <div class="viewport">
    <CtaBarBlack inputname="재시도" class="bottom-bar" />
    <div class="textbox">
      앗! 오류가 발생했어요..<br />잠시 후에 다시 시도해주세요
    </div>
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
  justify-content: flex-end; /* 아래쪽으로 정렬 */
  margin: 0 auto; /* 여유 공간이 있을 경우 뷰포트를 중앙에 배치 */
  background-color: #fff; /* 선택 사항: 배경색 설정 */
  position: relative; /* 자식 요소의 위치를 상대적으로 설정하기 위한 컨텍스트 확보 */
}

.bottom-bar {
  margin-bottom: 133px; /* 하단에서 약간의 공간 추가 */
}

/* html과 body가 뷰포트의 전체 높이를 차지하도록 설정 */
html,
body {
  height: 100%;
  margin: 0;
  padding: 0;
  overflow: hidden; /* body의 스크롤 방지 */
}
</style>
