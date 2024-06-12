<template>
  <div v-if="showModal" class="modal-overlay" @click="closeModal">
    <div class="modal-content" @click.stop>
      <video class="modal-video" autoplay muted playsinline loop>
        <source src="../assets/coin_spin.webm" type="video/webm" />
        Your browser does not support the video tag.
      </video>
      <h1 class="modal-title">같은 날에 다른 여행이 있어요</h1>
      <p class="modal-subtitle">계속해서 등록하시겠어요?</p>
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
          등록할래요
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, defineEmits } from 'vue';

const emit = defineEmits(['close', 'confirm']);

const showModal = ref(true); // 모달이 열려 있는지 여부를 추적하는 변수
const isNoSelected = ref(false); // '안할래요' 버튼이 선택되었는지 여부를 추적하는 변수
const isYesSelected = ref(false); // '등록할래요' 버튼이 선택되었는지 여부를 추적하는 변수

function selectNo() {
  isNoSelected.value = true;
  isYesSelected.value = false;
  closeModal(false); // false를 전달하여 모달을 닫음
}

function selectYes() {
  isYesSelected.value = true;
  isNoSelected.value = false;
  closeModal(true); // true를 전달하여 모달을 닫음
}

function closeModal(value) {
  showModal.value = false; // showModal 변수를 false로 설정하여 모달을 닫음
  emit('close', value); // 이벤트를 발생시켜 부모 컴포넌트에 값을 전달함
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
  border-radius: 20px;
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
  font-size: 55px;
  font-weight: bolder;
  margin-bottom: 20px;
}

.modal-subtitle {
  font-size: 35px;
  color: grey;
  margin-bottom: 60px;
}

.modal-buttons {
  display: flex;
  gap: 30px;
}

/* .modal-button:hover {
  background: #2e3240;
/* color: white;
} */
/* 2024.06.12 호버삭제 */

/* .modal-button-no:not(.faded) {
  background: #3e444e;
  color: white;} */

.modal-button-no {
  font-style: normal;
  font-weight: 600;
  font-size: 40px;
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
  font-weight: 500;
  font-size: 40px;
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
