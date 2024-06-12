<template>
  <div class="box">
    <div class="header-input">
      <input class="inputBox" :placeholder="TextInput" />
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';
const props = defineProps({
  headerInput: String,
  TextInput: String,
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
  text-align: center;
  color: white;
  margin: auto; /* 가운데 정렬 */
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 30px;
  text-align: center;
}

.header-input {
  display: flex;
  align-items: center;
  width: 1080px;
  height: 184px;
}

.inputBox {
  width: 965px;
  height: 173px;
  background: #f5f6f7;
  border-radius: 46px;
  font-style: normal;
  font-weight: 400;
  font-size: 52px;
  line-height: 1.3;
  text-align: left;
  color: #caced4; /* 기본 상태 색상 */
  padding: 0 20px;
  border: none; /* Remove border when not focused */
}

.inputBox::placeholder {
  color: #caced4; /* 플레이스홀더 색상 */
}

.inputBox:focus {
  outline: none;
  color: #353b43; /* 포커스 상태 색상 */
}
</style>
