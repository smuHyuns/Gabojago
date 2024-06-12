<template>
  <div class="container">
    총 지출 금액 중<br />
    {{ expenseCategory }} 사용이 가장 많았어요
  </div>
</template>

<script setup>
import { ref } from "vue";

const expenseCategory = ref(""); // 빈 문자열로 초기화

// 다른 곳에서 이 변수에 값을 할당하여 동적으로 변경할 수 있습니다.
expenseCategory.value = "식비"; // 예시 데이터, 실제로는 해당 데이터를 받아와야 합니다.
</script>

<style scoped>
.container {
  font-family: Pretendard;
  width: 100%;
  font-style: normal;
  font-weight: 600;
  font-size: 75px;
  line-height: 1.4;
  text-align: left;
  width: 965px;
  margin: 0 auto;
  color: #3e444e;
}
</style>
