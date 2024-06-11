<template>
  <div>
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

    <div>
      <ul>
        <li v-for="country in filteredCountries" :key="country">
          {{ country }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import TopbarXSign from '@/components/TopbarXSign.vue';
import CountrySearch from '@/components/CountrySearch.vue';

const searchQuery = ref('');
const selectedCategory = ref('전체');

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
    '대한민국',
    '일본',
    '중국',
    '인도',
    '베트남',
    '태국',
    '말레이시아',
    '필리핀',
    '프랑스',
    '독일',
    '영국',
    '이탈리아',
    '스페인',
    '네덜란드',
    '스위스',
    '러시아',
    '미국',
    '캐나다',
    '멕시코',
    '쿠바',
    '파나마',
    '바하마',
    '코스타리카',
    '자메이카',
    '브라질',
    '아르헨티나',
    '칠레',
    '페루',
    '콜롬비아',
    '볼리비아',
    '에콰도르',
    '우루과이',
    '남아프리카 공화국',
    '이집트',
    '모로코',
    '나이지리아',
    '케냐',
    '에티오피아',
    '탄자니아',
    '알제리',
    '호주',
    '뉴질랜드',
    '피지',
    '파푸아뉴기니',
    '사모아',
    '통가',
    '바누아투',
    '솔로몬 제도',
  ],
  아시아: [
    '대한민국',
    '일본',
    '중국',
    '인도',
    '베트남',
    '태국',
    '말레이시아',
    '필리핀',
    '싱가포르',
    '인도네시아',
  ],
  유럽: [
    '프랑스',
    '독일',
    '영국',
    '이탈리아',
    '스페인',
    '네덜란드',
    '스위스',
    '러시아',
    '벨기에',
    '포르투갈',
  ],
  북아메리카: [
    '미국',
    '캐나다',
    '멕시코',
    '쿠바',
    '파나마',
    '바하마',
    '코스타리카',
    '자메이카',
  ],
  남아메리카: [
    '브라질',
    '아르헨티나',
    '칠레',
    '페루',
    '콜롬비아',
    '볼리비아',
    '에콰도르',
    '우루과이',
    '파라과이',
    '베네수엘라',
  ],
  아프리카: [
    '남아프리카 공화국',
    '이집트',
    '모로코',
    '나이지리아',
    '케냐',
    '에티오피아',
    '탄자니아',
    '알제리',
  ],
  오세아니아: [
    '호주',
    '뉴질랜드',
    '피지',
    '파푸아뉴기니',
    '사모아',
    '통가',
    '바누아투',
    '솔로몬 제도',
  ],
};

const filteredCountries = computed(() => {
  let filtered = countries[selectedCategory.value];
  if (searchQuery.value) {
    filtered = filtered.filter((country) =>
      country.includes(searchQuery.value)
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
  margin-top: 10px; /* 여기에 원하는 값을 설정하여 거리를 조절 */
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
  height: 1160px;
  width: 1080px;
  background-color: var(--grey-0);
  margin: 0;
  position: relative; /* 버튼 위치 설정을 위한 기준 요소 */
}

.kindTripBox ul {
  display: flex;
  overflow-x: auto; /* 수평스크롤 활성화 */
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
</style>
