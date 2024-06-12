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
      </div>
    </div>
    <div class="content" v-if="activeTitle === 'onetitle'">
      <!-- onetitle에 대한 내용 -->
      <p>{{ firstInput }}</p>
    </div>
    <div class="content" v-if="activeTitle === 'twotitle'">
      <!-- twotitle에 대한 내용 -->
      <p>{{ secondInput }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const props = defineProps({
  onetitle: String,
  twotitle: String,
  firstInput: String,
  secondInput: String,
});

// 초기 activeTitle 값을 'onetitle'로 설정
const activeTitle = ref('onetitle');

const toggle = (title) => {
  // activeTitle 값이 변경될 때 항상 하나의 타이틀이 선택되도록 설정
  if (activeTitle.value !== title) {
    activeTitle.value = title;
  }
};
</script>

<style scoped>
.titlebox {
  width: 965px;
  height: 150px;
  /* padding: 17.28px; */
  background: #f5f6f7;
  border-radius: 28.8px;
  display: flex;
  justify-content: center; /* 그룹을 중앙 정렬 */
  align-items: center; /* 수직으로 중앙 정렬 */
}

.titles {
  display: flex;
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
