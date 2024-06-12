<template>
  <div class="viewport">
    <TopbarXSign titleText="여행 검색" />

    <div class="search-container">
      <CountrySearch
        v-model="searchQuery"
        @search="filterCountries"
        placeholder="찾으시는 국가명을 입력해주세요"
      />
    </div>

    <div class="whiteBox">
      <div class="kindTripBox">
        <ul>
          <li
            v-for="(category, index) in categories"
            :key="index"
            class="tripCategory"
            :class="{ selected: selectedCategory === category }"
            @click="selectCategory(category)"
          >
            <div class="tripCategoryName">{{ category }}</div>
          </li>
        </ul>
      </div>
    </div>
    <div class="country-buttons-box">
      <div class="country-buttons">
        <CountryButton
          v-for="country in filteredCountries"
          :key="country.name"
          :countryName="country.name"
          :flagSrc="getFlagSrc(country.flag)"
          :isSelected="selectedCountries.includes(country.name)"
          @update:isSelected="updateSelectedCountries(country.name)"
        />
      </div>
      <div class="country-button" @click="toggleSelected">
        <img :src="flagSrc" />
        <span class="country-name">{{ countryName }}</span>
      </div>
    </div>
    <CtaBar inputname="다음으로" :on="isblack" />
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import TopbarXSign from '@/components/TopbarXSign.vue';
import CountrySearch from '@/components/CountrySearch.vue';
import CountryButton from '@/components/CountryButton.vue';
import CtaBar from '@/components/CtaBar.vue';

const searchQuery = ref('');
const selectedCategory = ref('전체');
const selectedCountries = ref([]);
const isblack = ref(false);

const categories = [
  '전체',
  '아시아',
  '유럽',
  '북아메리카',
  '남아메리카',
  '아프리카',
  '오세아니아',
];

const countries = {
  전체: [
    { name: '대한민국', flag: 'korea.png' },
    { name: '일본', flag: 'japan.png' },
    { name: '중국', flag: 'china.png' },
    { name: '베트남', flag: 'vietnam.png' },
    { name: '태국', flag: 'thailand.png' },
    { name: '필리핀', flag: 'philippines.png' },
    { name: '인도', flag: 'india.png' },
    { name: '프랑스', flag: 'france.png' },
    { name: '독일', flag: 'germany.png' },
    { name: '미국', flag: 'usa.png' },
    { name: '캐나다', flag: 'canada.png' },
    { name: '브라질', flag: 'brazil.png' },
    { name: '아르헨티나', flag: 'argentina.png' },
    { name: '이집트', flag: 'egypt.png' },
    { name: '남아프리카 공화국', flag: 'southafrica.png' },
    { name: '호주', flag: 'australia.png' },
    { name: '뉴질랜드', flag: 'newzealand.png' },
    { name: '인도네시아', flag: 'indonesia.png' },
    { name: '싱가포르', flag: 'singapore.png' },
    { name: '라오스', flag: 'laos.png' },
    // ... 다른 국가들 추가
  ],
  아시아: [
    { name: '대한민국', flag: 'korea.png' },
    { name: '일본', flag: 'japan.png' },
    { name: '중국', flag: 'china.png' },
    { name: '베트남', flag: 'vietnam.png' },
    { name: '태국', flag: 'thailand.png' },
    { name: '필리핀', flag: 'philippines.png' },
    { name: '인도', flag: 'india.png' },
    { name: '인도네시아', flag: 'indonesia.png' },
    { name: '싱가포르', flag: 'singapore.png' },
    { name: '라오스', flag: 'laos.png' },

    // ... 나머지 아시아 국가들
  ],
  유럽: [
    { name: '프랑스', flag: 'france.png' },
    { name: '독일', flag: 'germany.png' },
    // ... 나머지 유럽 국가들
  ],
  북아메리카: [
    { name: '미국', flag: 'usa.png' },
    { name: '캐나다', flag: 'canada.png' },
    // ... 나머지 북아메리카 국가들
  ],
  남아메리카: [
    { name: '브라질', flag: 'brazil.png' },
    { name: '아르헨티나', flag: 'argentina.png' },
    // ... 나머지 남아메리카 국가들
  ],
  아프리카: [
    { name: '이집트', flag: 'egypt.png' },
    { name: '남아프리카 공화국', flag: 'southafrica.png' },

    // ... 나머지 아프리카 국가들
  ],
  오세아니아: [
    { name: '호주', flag: 'australia.png' },
    { name: '뉴질랜드', flag: 'newzealand.png' },
    // ... 나머지 오세아니아 국가들
  ],
};

function getFlagSrc(flag) {
  return new URL(`../assets/country_flag/${flag}`, import.meta.url).href;
}

const filteredCountries = computed(() => {
  let filtered = countries[selectedCategory.value];
  if (searchQuery.value) {
    filtered = filtered.filter((country) =>
      country.name.includes(searchQuery.value)
    );
  }
  return filtered;
});

function filterCountries() {
  // 이 함수는 @search 이벤트 핸들러로 사용됩니다.
}

function selectCategory(category) {
  selectedCategory.value = category;
  searchQuery.value = ''; // 새로운 카테고리를 선택하면 검색어를 초기화합니다.
}

function updateSelectedCountries(countryName) {
  const index = selectedCountries.value.indexOf(countryName);
  if (index === -1) {
    selectedCountries.value.push(countryName);
  } else {
    selectedCountries.value.splice(index, 1);
  }
  isblack.value = selectedCountries.value.length >= 1;
  console.log(isblack.value);
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
.search-container {
  margin-top: 10px;
}

.kindTripBox {
  max-width: 1080px;
  height: 100px;
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px;
  overflow-x: auto; /* 내용이 넘칠 경우 수평 스크롤 */
  justify-content: flex-start; /* 좌측 정렬 */
  margin: 30px;
}

.whiteBox {
  height: 130px;
  width: 1080px;
  background-color: var(--grey-0);
  margin: 0;
  position: relative; /* 버튼 위치 설정을 위한 기준 요소 */
}

.kindTripBox ul {
  display: flex;
  overflow-x: auto; /* 수평스크롤 활성화 */
  overflow-y: hidden; /* 수직스크롤 비활성화 */
  gap: 20px; /* li 요소 간의 간격을 조금 더 키움 */
  list-style: none; /* 기본 리스트 스타일 제거 */
  padding: 0; /* 기본 패딩 제거 */
  margin: 0; /* 기본 마진 제거 */
}

.tripCategory {
  height: 60px; /* 높이를 더 키움 */
  justify-content: center; /* 중앙 정렬 */
  align-items: center; /* 중앙 정렬 */
  gap: 15px; /* 간격을 더 키움 */
  display: inline-flex;
  background: var(--grey-200); /* 기본 배경 색상 */
  border-radius: 40px;
  padding: 20px 25px; /* 패딩을 더 키움 */
  font-size: 30px; /* 글자 크기를 더 키움 */
  color: var(--grey-700); /* 기본 글자 색상 */
  white-space: nowrap; /* 텍스트가 줄 바꿈 되지 않도록 */
}

.tripCategory:hover {
  background-color: var(--grey-700); /* 선택된 배경 색상 */
  color: white !important; /* 선택된 글자 색상 */
  transition-duration: 0.8s;
}

.tripCategory.selected {
  background-color: var(--grey-700); /* 선택된 배경 색상 */
  color: white !important; /* 선택된 글자 색상 */
}

.tripCategoryName,
.tripCategoryCount {
  font-weight: 600;
  word-wrap: break-word;
  color: inherit; /* 부모 요소의 글자 색상 상속 */
}

.tripCategoryCount {
  font-size: 22px; /* 글자 크기를 더 키움 */
}

.country-buttons-box {
  width: 1080px;
  height: 1350.72px;
  overflow-y: scroll;
  padding-bottom: 70px;
}

.country-buttons {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
  padding: 20px;
}
</style>
