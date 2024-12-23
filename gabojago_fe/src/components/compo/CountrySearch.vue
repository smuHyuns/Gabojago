<template>
  <div class="box">
    <div class="header-input">
      <div class="input-container">
        <div class="search-icon">
          <img src="@/assets/search.png" alt="Search Icon" />
        </div>
        <input
          class="inputBox"
          :placeholder="placeholder"
          v-model="inputValue"
          @keypress.enter="$emit('search', inputValue)"
        />
      </div>
      <div class="header">{{ header }}</div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits, ref, watch } from 'vue';

const props = defineProps({
  header: String,
  placeholder: String,
  modelValue: String,
});

const emit = defineEmits(['update:modelValue', 'search']);

const inputValue = ref(props.modelValue);

watch(
  () => props.modelValue,
  (newValue) => {
    inputValue.value = newValue;
  }
);

watch(inputValue, (newValue) => {
  emit('update:modelValue', newValue);
});
</script>

<style scoped>
.header-input {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.input-container {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
}

.inputBox {
  width: 885px;
  height: 138px;
  background: #f5f6f7;
  padding-left: 120px; /* 아이콘의 너비와 추가 마진 */
  border-radius: 28.8px;
  font-style: normal;
  font-weight: 500;
  font-size: 46px;
  line-height: 1.3;
  text-align: left;
  color: #353b43; /* 기본 상태 색상 */
  border: none; /* Remove border when not focused */
}

.inputBox::placeholder {
  color: #caced4;
}

.inputBox:focus {
  outline: none;
  color: #353b43;
}

.inputBox:focus::placeholder {
  color: transparent;
}

.search-icon {
  position: absolute;
  left: 35px; /* 아이콘을 inputBox 안의 왼쪽에 정렬 */
  top: 50%;
  transform: translateY(-50%);
  width: 70px; /* 아이콘의 너비를 70%로 줄임 */
  height: 70px; /* 아이콘의 높이를 70%로 줄임 */
}

.search-icon img {
  width: 100%;
  height: 100%;
  object-fit: contain; /* 아이콘 크기 조정 */
}

.header {
  font-style: normal;
  font-weight: 700;
  font-size: 52px;
  line-height: 1;
  text-align: center;
  color: #353b43;
  margin-top: 20px; /* 위와의 간격 추가 */
}
</style>
