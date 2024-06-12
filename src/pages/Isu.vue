<template>
  <div class="viewport">
    <Topbar titleText="마이페이지" />
    <TextInput headerInput="이름" TextInput="변경할 이름을 입력해 주세요" />
    <div class="spacer"></div>
    <CtaBar inputname="수정하기" class="bottom-bar" />
  </div>
</template>

<script setup>
import Topbar from '@/components/Topbar.vue';
import TextInput from '@/components/TextInput.vue';
import CtaBar from '@/components/CtaBar.vue';
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
}

.spacer {
  flex-grow: 1; /* Topbar와 TextInput 사이의 공간을 채우기 위해 사용 */
}

/* CtaBar 컴포넌트의 bottom-bar 클래스에 적용될 스타일 */
.bottom-bar {
  margin-top: 30px; /* CtaBar와 상단 사이에 30px의 여백을 추가합니다. */
}
</style>
