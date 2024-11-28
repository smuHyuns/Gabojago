<template>
  <div class="topbarBox">
    <div class="imgbox">
      <img class="icon" src="@/assets/chevron-right.png" @click="goBack" />
      <div class="title">{{ titleText }}</div>
      <button class="Changeicon" @click="navigateToDetailPage">
        <img
          src="@/assets/휴지통.png"
          alt="Icon"
          class="icon2"
          @click="goBack"
        />
      </button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const props = defineProps({
  titleText: {
    type: String,
    required: true,
  },
  tripId: {
    type: Number,
    required: true,
  },
  selectedDate: {
    type: String,
    required: true,
  },
});

function goBack() {
  router.back();
}
</script>

<style scoped>
.topbarBox {
  margin-top: 10%;
  width: 100%;
  padding-top: 19px;
  padding-bottom: 19px;
  padding-left: 50px;
  padding-right: 237px;
  display: flex;
  align-items: center;
}

.imgbox {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-left: 20px;
  height: 91px;
}

.icon {
  height: 75px;
  object-fit: contain;
  cursor: pointer;
}

.title {
  color: #353b43;
  font-size: 58px;
  font-weight: 700;
  line-height: 91px;
}

.icon2 {
  width: 69.12px;
  height: 69.12px;
  padding: 8.64px;
  justify-content: center;
  align-items: center;
  display: inline-flex;
}

.Changeicon {
  left: 400px;
  background: none;
  border: none;
  position: relative;
}
</style>
