<template>
  <div class="history-list-container" @click="toggleSelection">
    <div class="history-img-box">
      <img
        class="history-icon"
        v-bind:src="flagSrc"
        :style="{
          filter:
            type === '추가'
              ? 'hue-rotate(371deg) brightness(1) saturate(7)'
              : 'none',
        }"
        alt="Selected Icon"
      />
    </div>
    <div class="histroy-list">{{ description }}</div>
    <div class="history-money">
      <div class="history-money-won">{{ number }}원</div>
      <div class="history-money-local">{{ number2 }}JPY</div>
    </div>
    <div class="history-check-box">
      <img
        class="history-check-icon"
        :class="{ visible: isSelected, selected: isSelected }"
        src="@/assets/check-circle.png"
        alt="Selected Icon"
      />
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
  isSelected: Boolean,
  description: String,
  number: Number,
  number2: Number,
  flagSrc: String,
  type: String,
});

const emit = defineEmits(['update:isSelected']);

function toggleSelection() {
  emit('update:isSelected', !props.isSelected);
}
</script>

<style scoped>
.history-list-container {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  height: 216px;
  margin: 0;
  padding: 0 em 0 4em;
  gap: 2em;
}

.history-img-box {
  margin: 0;
  width: 140px;
  height: 140px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 1em;
  padding-left: 30px;
}

.histroy-list {
  margin: 0;
  font-size: 3em;
  flex-grow: 1;
}

.history-money {
  margin: 0;
  width: 300px;
}

.history-money-won {
  text-align: right;
  font-size: 3em;
}

.history-money-local {
  text-align: right;
  font-size: 1.5em;
  color: #999;
}

.history-check-box {
  margin: 0;
  width: 100px;
  height: 140px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.history-icon {
  width: 100px;
  height: 100px;
  padding-right: 30px;
}

.history-check-icon {
  width: 60px;
  height: 60px;
  opacity: 0.5;
  padding-right: 30px;
}

.history-check-icon.visible {
  opacity: 1; /* 선택된 상태에서 선명하게 설정 */
}

.selected {
  /* 클릭 시 배경색을 변경하지 않도록 설정 */
  background-color: transparent;
}
</style>
