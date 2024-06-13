<template>
  <div class="TopSelect">
    <div class="titlebox">
      <div class="titles">
        <div
          class="onetitle"
          :class="{ active: activeTitle === 'onetitle' }"
          @click="toggle('onetitle')"
        >
          {{ onetitle }}
        </div>
        <div
          class="twotitle"
          :class="{ active: activeTitle === 'twotitle' }"
          @click="toggle('twotitle')"
        >
          {{ twotitle }}
        </div>
        <div class="highlight" :style="{ left: highlightPosition }"></div>
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
const highlightPosition = ref('0px');

const emit = defineEmits(['updateType']);

const toggle = (title) => {
  if (activeTitle.value !== title) {
    activeTitle.value = title;
    highlightPosition.value = title === 'twotitle' ? '50px' : '0px';
    emit('updateType', title === 'twotitle' ? '추가' : '지출');
  }
};

// 초기 상태에 따라 업데이트
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
  overflow: hidden;
}

.onetitle,
.twotitle {
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
}

.twotitle.active,
.onetitle.active {
  background: #ffffff;
  box-shadow: 0px 0px 14.4px rgba(0, 0, 0, 0.04);
  color: #353b43;
  font-weight: 600;
}
</style>
