<template>
  <div class="ctaBar">
    <button
      :class="{ 'cta-bar-black': props.on, 'cta-bar': !props.on }"
      @click="handleClick"
    >
      {{ props.inputname }}
    </button>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
  inputname: String,
  on: Boolean,
});

const emit = defineEmits(['submit']);

const handleClick = () => {
  if (props.on) {
    emit('submit');
  }
};
</script>

<style scoped>
.cta-bar {
  position: absolute;
  bottom: 150px;
  left: 50%;
  transform: translateX(-50%);
  width: 965px;
  height: 152px;
  background: #eaecef;
  font-size: 46px;
  color: #8892a0;
  font-style: normal;
  font-weight: 600;
  line-height: 1.3;
  text-align: center;
  border-radius: 30px;
  border: none;
}

.cta-bar-black {
  position: absolute;
  bottom: 150px;
  left: 50%;
  transform: translateX(-50%);
  width: 965px;
  height: 152px;
  background: #3e444e;
  color: #ffffff;
  font-style: normal;
  font-weight: 600;
  font-size: 46px;
  line-height: 1.3;
  text-align: center;
  border-radius: 30px;
  border: none;
}
</style>
