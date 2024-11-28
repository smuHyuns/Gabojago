<template>
  <div class="box">
    <div class="header-input">
      <div class="header">{{ headerInput }}</div>
      <input
        class="inputBox"
        :placeholder="placeholder"
        v-model="inputValue"
        @input="$emit('update:modelValue', inputValue)"
      />
    </div>
  </div>
</template>

<script setup>
import { defineProps, ref, watch } from 'vue';

const props = defineProps({
  headerInput: String,
  modelValue: String,
  placeholder: String,
});

const inputValue = ref(props.modelValue || '');

// modelValue prop이 변경될 때 inputValue도 업데이트
watch(
  () => props.modelValue,
  (newValue) => {
    inputValue.value = newValue;
  }
);
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
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 20px;
}

.header-input {
  display: flex;
  align-items: center;
  width: 1080px;
  height: 184px;
}

.header {
  font-family: Pretendard;
  font-style: normal;
  font-weight: 600;
  font-size: 45px;
  line-height: 1;
  text-align: left;
  color: #353b43;
  width: 150px;
  height: 52px;
}

.inputBox {
  width: 680px;
  height: 138px;
  background: #f5f6f7;
  border-radius: 28.8px;
  font-family: Pretendard;
  font-style: normal;
  font-weight: 400;
  font-size: 46px;
  padding-left: 40px; /* 아이콘의 너비와 추가 마진 */
  line-height: 1.3;
  text-align: left;
  color: #353b43; /* 기본 상태 색상 */
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
