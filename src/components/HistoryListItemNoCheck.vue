<template>
  <div class="history-list-container">
    <div class="history-img-box">
      <img class="history-img" :src="img" alt="Selected Icon" />
    </div>
    <div class="histroy-list">{{ list }}</div>
    <div class="history-money">
      <div :class="['history-money-won', { income: type === '추가', expense: type === '지출' }]">{{ type === '추가' ? '+' : '-' }} {{ Math.abs(number).toLocaleString() }}원</div>
      <div class="history-money-local">{{ number2.toLocaleString() }} JPY</div>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';

const props = defineProps({
  list: String,
  number: Number,
  number2: Number,
  img: String,
  type: String, // 타입을 추가합니다.
});

console.log(props.type); // type이 올바르게 전달되는지 확인하는 디버그 로그
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

.history-money-won.income {
  color: blue;
}

.history-money-won.expense {
  color: red;
}

.history-money-local {
  text-align: right;
  font-size: 1.5em;
  color: #999;
}
</style>
