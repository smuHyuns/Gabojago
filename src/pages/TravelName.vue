<template>
  <div class="viewport">
    <Topbar titleText="여행 제목" />
    <TextInput2
      placeholder="제목을 입력해주세요"
      v-model="inputname"
      @input="limitInput"
    />
    <div class="spacer"></div>
    <CtaBar
      class="bottom-bar"
      inputname="여행 등록하기"
      :on="isblack"
      @submit="updateNickname"
    />
  </div>
</template>

<script setup>
import Topbar from '@/components/Topbar.vue';
import TextInput2 from '@/components/TextInput2.vue';
import CtaBar from '@/components/CtaBar.vue';
import { ref } from 'vue';

const isblack = ref(false);
let inputname = ref('');

const limitInput = () => {
  if (inputname.value.length > 10) {
    inputname.value = inputname.value.slice(0, 10);
  }
  isblack.value = inputname.value.length >= 1;
};
</script>

<style scoped>
.viewport {
  width: 1080px;
  height: 2340px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  margin: 0 auto;
  background-color: #fff;
}

.spacer {
  flex-grow: 1;
}

.bottom-bar {
  margin-top: 30px;
}
</style>
