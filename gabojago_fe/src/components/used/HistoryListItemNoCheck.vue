<template>
  <div class="history-list-container">
    <div class="history-img-box">
      <img
        class="history-img"
        :src="img"
        :alt="list || 'No image available'"
        :style="{
          filter:
            type === '추가'
              ? 'hue-rotate(371deg) brightness(1) saturate(7)'
              : 'none',
        }"
      />
    </div>
    <div class="history-list">{{ list }}</div>
    <div class="history-money">
      <div
        :class="[
          'history-money-won',
          { income: type === '추가', expense: type === '지출' },
        ]"
      >
        {{ type === '추가' ? '+' : '-' }}
        {{ Math.abs(number2).toLocaleString() }}원
      </div>
      <div class="history-money-local">
        {{ number.toLocaleString() }} {{ currency }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';

// Props 정의
const props = defineProps({
  list: { type: String, required: true },
  number: { type: Number, required: true },
  number2: { type: Number, required: true },
  img: { type: String, required: true }, // 이미지 경로를 전달받음
  type: { type: String, default: '지출' },
  currency: { type: String, required: true, default: 'KRW' },
});
</script>

<style scoped>
.history-list-container {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  height: 216px;
  margin: 0;
  padding: 0 2em 0 1em;
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
}

.history-img {
  width: 80px;
  height: 80px;
}

.history-list {
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

.history-money-won.income {
  color: var(--blue-300); /* 수입 금액 색상 */
}

.history-money-won.expense {
  color: var(--red-300); /* 지출 금액 색상 */
}

.history-money-local {
  text-align: right;
  font-size: 1.5em;
  color: #999;
}
</style>
