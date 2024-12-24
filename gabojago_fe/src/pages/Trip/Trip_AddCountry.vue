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
          :isSelected="selectedCountries === country.name"
          @update:isSelected="updateSelectedCountry(country)"
        />
      </div>
    </div>
    <CtaBar inputname="다음으로" :on="isblack" @click="navigateToCalendar" />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import TopbarXSign from '@/components/used/TopbarXSign.vue';
import CountrySearch from '@/components/used/CountrySearch.vue';
import CountryButton from '@/components/used/CountryButton.vue';
import CtaBar from '@/components/used/CtaBar.vue';
import { categories, countries } from '@/config/CountryConfig';
import { useAddTripStore } from '@/stores/tripStore';
import { useAuthStore } from '@/stores/auth';

const tripStore = useAddTripStore();
const authStore = useAuthStore();

const searchQuery = ref('');
const selectedCategory = ref('전체');
const selectedCountries = ref('');
const selectedCountry = ref('');
const isblack = ref(false);
const router = useRouter();

function getFlagSrc(flag) {
  return new URL(`/src/assets/country_flag/${flag}`, import.meta.url).href;
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

function filterCountries() {}

function selectCategory(category) {
  selectedCategory.value = category;
  searchQuery.value = '';
}

function updateSelectedCountry(country) {
  if (selectedCountries.value !== country.name) {
    selectedCountries.value = country.name;
    selectedCountry.value = country.translatedName;
  } else {
    selectedCountries.value = '';
    selectedCountry.value = '';
  }
  isblack.value = !!selectedCountries.value;
}

function navigateToCalendar() {
  tripStore.setCountry(selectedCountry.value);
  console.log('선택된 국가 : ', tripStore.country);
  if (isblack.value) {
    router.push({
      path: '/add-calendar',
    });
  }
}

onMounted(async () => {
  if (!authStore.token) {
    router.push('/login');
    return;
  }
  tripStore.setCountry(null);
});
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
  position: relative;
}

.search-container {
  margin-top: 10px;
}

.whiteBox {
  height: 130px;
  width: 1080px;
  background-color: var(--grey-0);
  margin: 0;
  position: relative; /* 버튼 위치 설정을 위한 기준 요소 */
}

.kindTripBox {
  max-width: 1080px;
  height: 184px;
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 0;
  overflow-x: auto; /* 내용이 넘칠 경우 수평 스크롤 */
  justify-content: flex-start; /* 좌측 정렬 */
  margin: 0 58px;
}

.kindTripBox ul {
  display: flex;
  gap: 20px;
  list-style: none;
  padding: 0;
  margin: 0;
}

.tripCategory {
  height: 94px; /* 높이를 더 키움 */
  justify-content: center; /* 중앙 정렬 */
  border-radius: 57px;
  padding: 20px 25px; /* 패딩을 더 키움 */
  color: var(--grey-700); /* 기본 글자 색상 */
  white-space: nowrap; /* 텍스트가 줄 바꿈 되지 않도록 */
}

.tripCategory {
  padding: 0 34px;
  gap: 15px;
  display: flex; /* 가로 정렬을 위해 flex로 설정 */
  align-items: center; /* 텍스트를 수직 가운데 정렬 */
  background: #f5f6f7;
  border-radius: 57px;
  font-size: 40px;
  font-weight: 500;
  color: #8892a0;
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
  margin-bottom: 30px;
}

.country-buttons {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
  padding: 20px;
}
</style>
