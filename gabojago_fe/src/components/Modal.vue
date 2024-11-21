<template>
  <div v-if="showModal" class="modal-overlay" @click="closeModal(false)">
    <div class="modal-content" @click.stop>
      <video class="modal-video" autoplay muted playsinline loop>
        <source src="../assets/coin_spin.webm" type="video/webm" />
        Your browser does not support the video tag.
      </video>
      <h1 class="modal-title">여행 경비를 추가해보세요</h1>
      <p class="modal-subtitle">경비는 언제든지 다시 추가할 수 있어요!</p>
      <div class="modal-buttons">
        <button
          class="modal-button-no"
          :class="{ faded: !isNoSelected }"
          @click="selectNo"
        >
          안할래요
        </button>
        <button
          class="modal-button-yes"
          :class="{ faded: !isYesSelected }"
          @click="selectYes"
        >
          추가할래요
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, defineEmits } from 'vue';

const emit = defineEmits(['close', 'confirm']);

const showModal = ref(true);
const isNoSelected = ref(false);
const isYesSelected = ref(false);

function selectNo() {
  isNoSelected.value = true;
  isYesSelected.value = false;
  emit('close', true); // true를 전달하여 안할래요를 선택했음을 알림
}

function selectYes() {
  isYesSelected.value = true;
  isNoSelected.value = false;
  closeModal(false); // 모달을 닫음
}

function closeModal(value) {
  showModal.value = false;
  emit('close', value);
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 999;
}

.modal-content {
  background: white;
  width: 800px;
  height: 650px;
  border-radius: 34px;
  padding: 60px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.modal-video {
  width: 254.27px;
  height: 318.55px;
  margin-bottom: 10px;
}

.modal-title {
  font-size: 63px;
  font-weight: 700;
  margin-bottom: 20px;
}

.modal-subtitle {
  font-size: 40px;
  color: grey;
  margin-bottom: 60px;
}

.modal-buttons {
  display: flex;
  gap: 30px;
}

.modal-button-no {
  font-style: normal;
  font-weight: 600;
  font-size: 46px;
  color: #8892a0;
  width: 350px;
  padding: 35px;
  border: none;
  border-radius: 15px;
  cursor: pointer;
  transition: background 0.3s, color 0.3s;
}

.modal-button-yes {
  font-style: normal;
  font-weight: 600;
  font-size: 46px;
  color: white;
  width: 350px;
  padding: 35px;
  border: none;
  border-radius: 15px;
  cursor: pointer;
  background: #3e444e;
  transition: background 0.3s, color 0.3s;
}
</style>
