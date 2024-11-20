<template>
  <div class="box">
    <div class="Member-add">
      <div class="icon-container">
        <div class="icon-left" @click="decrement">
          <img src="../assets/minus.png" alt="minus-icon" />
        </div>
        <div class="value-box">{{ displayValue }}</div>
        <div class="icon-right" @click="increment">
          <img src="../assets/plus.png" alt="plus-icon" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
const value = ref(0);

const increment = () => {
  value.value++;
};

const decrement = () => {
  if (value.value > 0) {
    value.value--;
  }
};

const displayValue = computed(() => {
  return value.value === 0 ? '혼자' : value.value;
});

const emit = defineEmits(['update:memberCount']);
watch(value, (newValue) => {
  emit('update:memberCount', newValue);
});
</script>

<style scoped>
.icon-container {
  display: flex;
  align-items: center;
}

.icon-left,
.icon-right {
  width: 69px;
  height: 69px;
  padding: 35px;
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
