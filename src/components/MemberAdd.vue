<template>
  <div class="box">
    <div class="Member-add">
      <!-- 왼쪽 아이콘과 입력 상자 오른쪽 아이콘을 div로 묶음 -->
      <div class="icon-container">
        <!-- 왼쪽 아이콘 -->
        <div class="icon-left" @click="decrement">
          <img src="../assets/minus.png" alt="minus-icon" />
        </div>
        <!-- 값이 표시될 박스 -->
        <div class="value-box">{{ displayValue }}</div>
        <!-- 오른쪽 아이콘 -->
        <div class="icon-right" @click="increment">
          <img src="../assets/plus.png" alt="plus-icon" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';

// 상태 변수로 변경
const value = ref(0);

const increment = () => {
  // 상태 변수 값 증가
  value.value++;
};

const decrement = () => {
  // 최소값이 0이 되도록 설정
  if (value.value > 0) {
    value.value--;
  }
};

// 값이 0일 때 "혼자" 표시
const displayValue = computed(() => {
  return value.value === 0 ? '혼자' : value.value;
});
</script>

<style scoped>
/* 나머지 스타일은 생략 */

.icon-container {
  display: flex;
  align-items: center; /* 아이콘과 입력 상자를 세로 중앙 정렬 */
}

.icon-left,
.icon-right {
  width: 69px;
  height: 69px;
  padding: 35px; /* 아이콘과 입력 상자 사이 간격 조정 */
}

.icon-left img,
.icon-right img {
  width: 100%;
  height: auto;
}

.value-box {
  width: 755px;
  height: 144px;
  font-family: Pretendard;
  font-style: normal;
  font-weight: 600;
  font-size: 52px;
  line-height: 1.5;
  letter-spacing: -0.02em;
  text-align: center;
  color: #353b43;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #f5f6f7;
  border-radius: 30px;
}
</style>
