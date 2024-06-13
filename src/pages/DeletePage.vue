<template>
  <TopbarWithIcon titleText="일주일 도쿄 여행" />
  <div class="day-box">날짜 db받기</div>
  <div class="scroll-box">
    <div class="country-buttons">
      <DeleteButtonSiwan
        v-for="country in filteredCountries"
        :key="country.list"
        :countryName="country.list"
        :flagSrc="country.flagSrc"
        :isSelected="selectedCountries.includes(country.list)"
        :number="country.number"
        :number2="country.number2"
        @update:isSelected="updateSelectedCountries(country.list)"
      />
      <!-- <DeleteButtonSiwan
        :key="1"
        countryName="일본"
        :flagSrc="'/src'"
        :isSelected="true"
        :number="4000"
        :number2="50000"
        @update:isSelected="updateSelectedCountries(country.list)"
      /> -->
    </div>
  </div>
  <CtaBar inputname="다음으로" :on="isblack" @click="navigateToCalendar" />
</template>

<script setup>
import { ref, computed } from 'vue';
import CtaBar from '@/components/CtaBar.vue';
import DeleteButtonSiwan from '@/components/DeleteButton-siwan.vue';
import TopbarWithIcon from '@/components/TopbarWithIcon.vue';

const searchQuery = ref('');
const selectedCategory = ref('전체');
const selectedCountries = ref([]);
const isblack = ref(false);

const countries = {
  전체: [
    {
      flagSrc: '/src/assets/관광.png',
      list: '관광',
      number: '- 5,109',
      number2: '- 580',
    },
    {
      flagSrc: '/src/assets/현금.png',
      list: '현금',
      number: '+ 100,000',
      number2: '+ 11,392.77',
    },
    {
      flagSrc: '/src/assets/현금.png',
      list: '현금',
      number: '+ 100,000',
      number2: '+ 5,000',
    },
    {
      flagSrc: '/src/assets/관광.png',
      list: '관광',
      number: '- 5,109',
      number2: '- 580',
    },
    // ... 다른 국가들 추가
  ],
};

const filteredCountries = computed(() => {
  let filtered = countries[selectedCategory.value];
  if (searchQuery.value) {
    filtered = filtered.filter((country) =>
      country.list.includes(searchQuery.value)
    );
  }
  return filtered;
});

function updateSelectedCountries(countryName) {
  if (selectedCountries.value.includes(countryName)) {
    selectedCountries.value = selectedCountries.value.filter(
      (name) => name !== countryName
    );
  } else {
    selectedCountries.value = [countryName];
  }
  isblack.value = selectedCountries.value.length >= 1;
}

function navigateToCalendar() {
  // Navigate to calendar logic
}
</script>

<style scoped>
.viewport {
  width: 1080px;
  height: 2340px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 0 auto;
  background-color: #fff;
}
.day-box {
  width: 1080px;
  height: 60px;
  background: #eaecef;
  text-align: left;
  display: flex;
  align-items: center;
  padding-left: 30px;
  font-size: 25px;
  color: #8892a0;
}
.scroll-box {
  margin: 0;
  width: 1080px;
  height: 1350.72px;
  overflow-y: scroll;
  padding-bottom: 70px;
}
</style>
