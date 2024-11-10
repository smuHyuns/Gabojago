<template>
  <div class="box">
    <div class="header-input">
      <input class="inputBox" :placeholder="TextInput" v-model="inputValue" />
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits, ref, watch } from 'vue';

const props = defineProps({
  headerInput: String,
  TextInput: String,
});

const emit = defineEmits(['update:modelValue']);

const inputValue = ref(props.modelValue);

watch(inputValue, (newValue) => {
  emit('update:modelValue', newValue);
});
</script>

<style scoped>
:root {
  --grey-0: #ffffff;
  --grey-100: #fbfbfc;
  --grey-200: #f5f6f7;
  --grey-300: #eaecef;
  --grey-400: #caced4;
  --grey-600: #8892a0;
  --grey-700: #616b79;
  --grey-800: #3e444e;
  --grey-900: #353b43;
  --grey-950: #16181c;
}

.inputBox:focus::placeholder {
  color: transparent;
}

.box {
  width: 1080px;
  max-height: 700px;
  text-align: center;
  color: white;
  margin: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 30px;
  text-align: center;
}

.header-input {
  display: flex;
  align-items: center;
  width: 1080px;
  height: 184px;
}

.inputBox {
  width: 910px;
  height: 152px;
  padding: 12px 16px;
  padding-left: 50px;
  margin-top: 20px;
  background: #f5f6f7;
  border-radius: 46px;
  font-style: normal;
  font-weight: 500;
  font-size: 50px;
  line-height: 1.3;
  color: #353b43;
  border: none;
  outline: none;
}

.inputBox::placeholder {
  color: #caced4;
}

.inputBox:focus {
  outline: none;
  color: #353b43;
}
</style>
