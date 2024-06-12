<template>
  <div class="box">
    <div class="firstBox">{{ firstInput }}</div>
    <div class="secondBox">{{ secondInput }}</div>
    <div class="thirdBox" @click="navigateToGun">{{ thirdInput }}</div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';
import { useRouter } from 'vue-router';
const router = useRouter();

const navigateToGun = () => {
  router.push('/gun');
};

const props = defineProps({
  firstInput: String,
  secondInput: String,
  thirdInput: String,
});
</script>

<style scoped>
:root {
  --grey-0: #ffffff;
  --grey-100: #fbfbfc;
  --grey-200: #f5f6f7;
  --grey-300: #eaecef;
  --grey-400: #caced4;
  --grey-600: #8892a0;
  --grey-700: #616b79;
  --grey-800: #3e444e;
  --grey-900: #353b43;
  --grey-950: #16181c;
}

.box {
  width: 1080px;
  max-height: 700px;
  /* border: 1px solid rgb(165, 157, 157); */
  text-align: center;
  color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 30px;
}

.firstBox {
  width: 1080px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 30px;
}

.secondBox {
  width: 1080px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 10px;
  margin-bottom: 25px;
  font-size: 60px;
  font-weight: bold;
}

.thirdBox {
  cursor: pointer;
  display: flex;
  flex-direction: column;
  padding: 36px 38px 36px 36px;
  gap: 10px;
  width: auto;
  left: calc(50% - 130px / 2 + 0.5px);
  top: 341px;
  background: #ffffff;
  border-radius: 60px;
  color: #3e444e;
  font-size: 40px;
  font-weight: bold;
}
</style>
