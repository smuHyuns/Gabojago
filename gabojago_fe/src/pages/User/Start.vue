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
      <source src="@/assets/slide_final.webm" type="video/webm" />
    </video>
    <CtaBarBlack inputname="다음으로" class="bottom-bar" @click="GoLogin" />
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import CtaBarBlack from '@/components/used/CtaBarBlack.vue';

const router = useRouter();

const GoLogin = () => {
  router.push('/login');
};
</script>

<style scoped>
.viewport {
  width: 1080px;
  height: 2340px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  margin: 0 auto;
  background-color: #fff;
  position: relative;
}
.textbox {
  text-align: center;
}
.start-msg {
  padding-top: 400px;
  font-weight: bold;
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
  margin-top: 16px;
}
.spacer {
  flex-grow: 1;
}

.bottom-bar {
  margin-bottom: 133px;
  cursor: pointer;
  z-index: 8888;
}
</style>
