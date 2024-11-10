<template>
  <div class="TopSelect">
    <div class="titlebox">
      <div class="titles">
        <div class="slider" :style="{ transform: sliderPosition }"></div>
        <div
          class="onetitle title"
          :class="{ active: activeTitle === 'onetitle' }"
          @click="toggle('onetitle')"
        >
          {{ onetitle }}
        </div>
        <div
          class="twotitle title"
          :class="{ active: activeTitle === 'twotitle' }"
          @click="toggle('twotitle')"
        >
          {{ twotitle }}
        </div>
      </div>
    </div>
    <div class="content" v-if="activeTitle === 'onetitle'">
      <p>{{ firstInput }}</p>
    </div>
    <div class="content" v-if="activeTitle === 'twotitle'">
      <p>{{ secondInput }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';

const props = defineProps({
  onetitle: String,
  twotitle: String,
  firstInput: String,
  secondInput: String,
});

const activeTitle = ref('onetitle');

const sliderPosition = ref('translateX(0px)');

const emit = defineEmits(['updateType']);

const toggle = (title) => {
  if (activeTitle.value !== title) {
    activeTitle.value = title;
    sliderPosition.value =
      title === 'twotitle' ? 'translateX(452px)' : 'translateX(0px)';
    emit('updateType', title === 'twotitle' ? '추가' : '지출');
  }
};

emit('updateType', activeTitle.value === 'twotitle' ? '추가' : '지출');
</script>

<style scoped>
.titlebox {
  position: relative;
  width: 965px;
  height: 150px;
  background: #f5f6f7;
  border-radius: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.titles {
  display: flex;
  position: relative;
  width: 904px;
  overflow: hidden;
}

.title {
  width: 452px;
  height: 115px;
  border-radius: 18px;
  font-weight: 600;
  font-size: 46px;
  color: #8892a0;
  margin: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  transition: color 0.3s;
  z-index: 2; /* 제목이 슬라이더 위에 오도록 설정 */
}

.slider {
  position: absolute;
  top: 0;
  left: 0;
  width: 452px;
  height: 115px;
  background: #ffffff;
  border-radius: 18px;
  box-shadow: 0px 0px 14.4px rgba(0, 0, 0, 0.04);
  transition: transform 0.4s;
  z-index: 1; /* 슬라이더가 제목 아래에 오도록 설정 */
}

.twotitle.active,
.onetitle.active {
  color: #353b43;
  font-weight: 600;
}
</style>
