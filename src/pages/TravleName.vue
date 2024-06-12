<template>
  <div class="viewport">
    <Topbar titleText="여행 제목" />
    <div class="content">
      <InputBox TextInput="제목을 입력해 주세요" />
    </div>
    <CtaBar inputname="여행 등록하기" />
  </div>
</template>

<script setup>
import Topbar from '@/components/Topbar.vue';
import InputBox from '@/components/InputBox.vue';
import CtaBar from '@/components/CtaBar.vue';
</script>

<style scoped>
.viewport {
  width: 1080px;
  height: 2340px;
  overflow: hidden; /* Prevent scrolling if content exceeds the fixed size */
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between; /* 요소들을 위아래로 균등하게 분배 */
  margin: 0 auto; /* Center the viewport if there is additional space */
  background-color: #fff; /* Optional: Set a background color */
}

.content {
  flex: 1; /* Topbar와 CtaBar 사이의 공간을 채우기 위해 사용 */
}

/* Ensure that the body and html take up the full height of the viewport */
html,
body {
  height: 100%;
  margin: 0;
  padding: 0;
  overflow: hidden; /* Prevent scrolling on the body */
}
</style>
