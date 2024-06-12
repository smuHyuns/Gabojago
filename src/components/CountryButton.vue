<template>
  <div
    class="country-button"
    :class="{ selected: isSelected }"
    @click="toggleSelection"
  >
    <img class="flag" :src="flagSrc" :alt="countryName" />
    <span class="country-name">{{ countryName }}</span>
    <img
      class="check-icon"
      :class="{ visible: isSelected }"
      src="../assets/check-circle.png"
      alt="Selected Icon"
    />
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
  countryName: String,
  flagSrc: String,
  isSelected: Boolean,
});

const emit = defineEmits(['update:isSelected']);

function toggleSelection() {
  emit('update:isSelected', !props.isSelected);
}
</script>

<style scoped>
.country-button {
  width: 960px;
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  border-radius: 50px;
  margin-bottom: 20px;
  cursor: pointer;
  background-color: transparent; /* 배경 색상을 투명으로 설정 */
}

.flag {
  margin-left: 15px;
  margin-right: 25px;
  width: 80px;
  height: 60px;
}

.country-name {
  flex: 1;
  margin: 0 20px;
  font-size: 40px;
  font-weight: bold;
}

.check-icon {
  margin-left: 15px;
  margin-right: 15px;
  width: 60px;
  height: 60px;
  opacity: 0.3; /* 기본 상태에서 흐리게 설정 */
  transition: opacity 0.3s;
}

.check-icon.visible {
  opacity: 1; /* 선택된 상태에서 선명하게 설정 */
}

.selected {
  /* 클릭 시 배경색을 변경하지 않도록 설정 */
  background-color: transparent;
}
</style>
