<template>
  <div class="history-list-container">
    <div class="history-img-box">
      <img class="history-img" v-bind:src="img" alt="Selected Icon" />
    </div>
    <div class="histroy-list">{{ list }}</div>
    <div class="history-money">
      <div class="history-money-won">{{ number }}원</div>
      <div class="history-money-local">{{ number2 }}JPY</div>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  list: String,
  number: Number,
  number2: Number,
  img: String,
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
</style>
