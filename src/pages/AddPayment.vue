<!-- <template>
  <div class="payment-box">
    <Modal @close="handleModalClose" @confirm="handleModalConfirm" />
    <Topbar class="topbar" titleText="경비" />
    <TopSelect class="top-select" onetitle="지출 추가" twotitle="경비 추가" />
    <div class="price-box">
      <div class="price-details">
        <input v-model="displayAmountJPY" @input="updateAmountJPY" type="text" class="print-big-price" placeholder="0 JPY" />
      </div>
      <div class="type-of-money" style="margin-left: 75px">(일본 엔)</div>
      <div class="print-small-price">={{ conversionResult.KRW }} 원</div>
    </div>
    <div class="payType">
      <span class="title">지출 형태</span>
      <div class="payType-box">
        <div class="payType-list" :class="{ selected: paymentMethod === '현금' }" @click="selectPaymentMethod('현금')">현금</div>
        <div class="payType-list" :class="{ selected: paymentMethod === '카드' }" @click="selectPaymentMethod('카드')">카드</div>
      </div>
    </div>
    <div class="payDetail">
      <span class="title">지출 내용</span>
      <input v-model="expenseDetail" type="text" class="payDetail-input" placeholder="내용을 입력해 주세요" />
    </div>
    <div class="category">
      <span class="title">카테고리</span>
      <div class="category-box">
        <div class="category-box-component" v-for="category in categories" :key="category" @click="selectCategory(category)" :class="{ selected: selectedCategory === category }">
          <img class="category-box-img" :src="getCategoryImage(category)" style="object-fit: cover" />
          <div class="category-box-txt">{{ category }}</div>
        </div>
      </div>
    </div>
    <div class="ctabar">
      <CtaBar inputname="등록하기" @click="navigateToHyunsoo" />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';
import Topbar from '@/components/Topbar.vue';
import CtaBar from '@/components/CtaBar.vue';
import TopSelect from '@/components/TopSelect.vue';
import { useRouter, useRoute } from 'vue-router';
import Modal from '@/components/Modal.vue';

const router = useRouter();
const route = useRoute();

const amountJPY = ref(0);
const displayAmountJPY = ref('');
const conversionResult = ref({ KRW: 0 });
const expenseType = ref('지출');
const paymentMethod = ref('현금');
const expenseDetail = ref('');
const selectedCategory = ref('');

const selectedCountries = ref(route.query.countries ? route.query.countries.split(',') : []);
const selectedDates = ref(route.query.selectedDates ? JSON.parse(route.query.selectedDates) : []);
const memberCount = ref(route.query.memberCount ? parseInt(route.query.memberCount) : 0);
const travelTitle = ref(route.query.travelTitle || '');

const categories = ['관광', '교통', '쇼핑', '숙박', '음식', '항공', '기타'];

const generateUniqueId = () => {
  return Date.now() + Math.floor(Math.random() * 1000);
};

const handleModalClose = (confirm) => {
  if (confirm) {
    registerDefaultData().then(() => {
      router.push({ path: '/hyunsoo' });
    });
  }
};

const handleModalConfirm = () => {
  console.log('Modal confirmed');
};

const registerDefaultData = async () => {
  const expense = {
    id: generateUniqueId(),
    type: expenseType.value,
    paymentMethod: paymentMethod.value,
    description: '기본 등록',
    category: '기타',
    amount: 0,
    convertedAmount: 0,
  };

  const currentDate = new Date();
  const startDate = new Date(selectedDates.value[0]);
  const endDate = new Date(selectedDates.value[selectedDates.value.length - 1]);

  let daysUntilTrip = Math.ceil((startDate.getTime() - currentDate.getTime()) / (1000 * 60 * 60 * 24));
  if (daysUntilTrip < 0) daysUntilTrip = 0;

  let travelComplete = '다가오는 여행';
  if (currentDate >= startDate && currentDate <= endDate) {
    travelComplete = '여행 중';
  } else if (currentDate > endDate) {
    travelComplete = '완료';
  }

  const userId = '08ac';
  try {
    const response = await axios.get(`http://localhost:3000/users/${userId}`);
    const user = response.data;

    let totalBudget = 0;
    let usedBudget = 0;
    let remainingBudget = 0;
    if (expenseType.value === '추가') {
      totalBudget += expense.convertedAmount;
      remainingBudget += expense.convertedAmount;
    } else {
      totalBudget -= expense.convertedAmount;
      usedBudget += expense.convertedAmount;
    }

    const newTrip = {
      id: generateUniqueId(),
      daysUntilTrip,
      startPeriod: selectedDates.value[0],
      endPeriod: selectedDates.value[selectedDates.value.length - 1],
      category: selectedCategory.value,
      country: selectedCountries.value.join(','),
      type: expenseType.value,
      travelComplete,
      describe: travelTitle.value,
      headcount: memberCount.value,
      expenses: [expense],
      totalBudget,
      usedBudget,
      remainingBudget,
    };

    user.trips.push(newTrip);

    await axios.put(`http://localhost:3000/users/${userId}`, user);
    console.log('Default data registered successfully');
  } catch (error) {
    console.error('Failed to register default data:', error);
  }
};

const navigateToHyunsoo = async () => {
  const expense = {
    id: generateUniqueId(),
    type: expenseType.value,
    paymentMethod: paymentMethod.value,
    description: expenseDetail.value,
    category: selectedCategory.value,
    amount: parseFloat(displayAmountJPY.value.replace(' JPY', '')),
    convertedAmount: parseFloat(conversionResult.value.KRW),
  };

  const currentDate = new Date();
  const startDate = new Date(selectedDates.value[0]);
  const endDate = new Date(selectedDates.value[selectedDates.value.length - 1]);

  let daysUntilTrip = Math.ceil((startDate.getTime() - currentDate.getTime()) / (1000 * 60 * 60 * 24));
  if (daysUntilTrip < 0) daysUntilTrip = 0;

  let travelComplete = '다가오는 여행';
  if (currentDate >= startDate && currentDate <= endDate) {
    travelComplete = '여행 중';
  } else if (currentDate > endDate) {
    travelComplete = '완료';
  }

  const userId = '08ac';
  try {
    const response = await axios.get(`http://localhost:3000/users/${userId}`);
    const user = response.data;

    let totalBudget = 0;
    let usedBudget = 0;
    let remainingBudget = 0;
    if (expenseType.value === '추가') {
      totalBudget += expense.convertedAmount;
      remainingBudget += expense.convertedAmount;
    } else {
      totalBudget -= expense.convertedAmount;
      usedBudget += expense.convertedAmount;
    }

    const newTrip = {
      id: generateUniqueId(),
      daysUntilTrip,
      startPeriod: selectedDates.value[0],
      endPeriod: selectedDates.value[selectedDates.value.length - 1],
      category: selectedCategory.value,
      country: selectedCountries.value.join(','),
      type: expenseType.value,
      travelComplete,
      describe: travelTitle.value,
      headcount: memberCount.value,
      expenses: [expense],
      totalBudget,
      usedBudget,
      remainingBudget,
    };

    user.trips.push(newTrip);

    await axios.put(`http://localhost:3000/users/${userId}`, user);
    console.log('Trip added successfully');
  } catch (error) {
    console.error('Failed to add trip:', error);
  }

  router.push({
    path: '/hyunsoo',
  });
};

async function fetchExchangeRate(from, to) {
  try {
    const apiKey = 'ffb7b54e74ef452e0961d9d9';
    const proxyUrl = 'https://api.allorigins.win/get?url=';
    const apiUrl = `https://v6.exchangerate-api.com/v6/${apiKey}/pair/${from}/${to}`;
    const response = await fetch(proxyUrl + encodeURIComponent(apiUrl));
    const data = await response.json();
    const parsedData = JSON.parse(data.contents);
    if (parsedData.result === 'success') {
      return parsedData.conversion_rate;
    } else {
      console.error(`Failed to fetch exchange rate for ${from} to ${to}: ${parsedData['error-type']}`);
      return 0;
    }
  } catch (error) {
    console.error(`Failed to fetch exchange rate for ${from} to ${to}:`, error);
    return 0;
  }
}

async function convertCurrency() {
  if (amountJPY.value > 0) {
    const JPYtoKRW = await fetchExchangeRate('JPY', 'KRW');
    conversionResult.value.KRW = (amountJPY.value * JPYtoKRW).toFixed(2);
  } else {
    conversionResult.value.KRW = 0;
  }
}

function updateAmountJPY(event) {
  const value = event.target.value.replace(/\D/g, '');
  amountJPY.value = value ? parseFloat(value) : 0;
  displayAmountJPY.value = value ? `${value} JPY` : '';
  convertCurrency();
}

function selectExpenseType(type) {
  expenseType.value = type;
}

function selectPaymentMethod(method) {
  paymentMethod.value = method;
}

function selectCategory(category) {
  selectedCategory.value = category;
}

function getCategoryImage(category) {
  try {
    return new URL(`/src/assets/${category}.png`, import.meta.url).href;
  } catch (e) {
    return new URL(`/src/assets/default.png`, import.meta.url).href;
  }
}
</script>

<style scoped>
.category-box .selected {
  /* background-color: var(--blue-200); 선택된 항목의 배경색 */
  filter: hue-rotate(350deg) brightness(1) saturate(7);
  color: white; /* 선택된 항목의 글자색 */
}
.payment-box {
  width: 1080px;
  height: 2340px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  margin: 0 auto;
  background-color: #fff;
}

.topbar {
  margin-bottom: 20px;
}

.box {
  width: 335px;
  height: 30px;
  padding: 30px;
  margin: 0;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 20px;
  font-size: 35px;
}

.selected {
  background-color: white;
}

.price-box {
  margin-top: 58px;
  margin-bottom: 28px;
  width: 965px;
  height: 288px;
  background-color: #f5f6f7;
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
}

.price-details {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 10px;
  color: #8892a0;
  margin-left: 75px;
}

.print-big-price {
  width: 500px;
  background-color: transparent;
  border: transparent;
  font-size: 80px;
  font-weight: 600;
  line-height: 80px;
}

.type-of-money {
  color: #8892a0;
  font-size: 35px;
  font-weight: 600;
  line-height: 35px;
  word-wrap: break-word;
}

.print-small-price {
  color: #8892a0;
  font-size: 46px;
  font-weight: 600;
  line-height: 46px;
  word-wrap: break-word;
  margin-top: 23px;
  margin-left: 75px;
}

.price-box-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
}

.payInfoBox {
  width: 964.8px;
  padding: 15px;
  border-radius: 20px;
  font-size: 40px;
  gap: 20px;
  background-color: #f5f6f7;
  margin: 25px auto;
}

.payType {
  width: 965px;
  height: 184px;
  border-radius: 20px;
  font-size: 52px;
  padding-bottom: 23.04px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title {
  margin: 0;
  font-weight: 600;
  font-size: 52px;
  line-height: 60px;
  color: #353b 43;
}

.payType-box {
  margin: 0;
  display: flex;
  gap: 30px;
}

.payType-list {
  width: 259px;
  height: 127px;
  border-radius: 30px;
  font-weight: 500;
  line-height: 46.08px;
  color: black;
  background-color: #f5f6f7;
  word-wrap: break-word;
  display: flex;
  align-items: center;
  justify-content: center;
}

.payType .selected {
  background: #616b79;
  color: white;
}

.payDetail {
  width: 965px;
  border-radius: 20px;
  font-size: 40px;
  gap: 20px;
  justify-content: space-between;
  display: flex;
  align-items: center;
}

.payDetail-input {
  margin: 0;
  width: 650px;
  height: 138px;
  padding: 0 40px; /* 텍스트 왼쪽 여백 추가 */
  background-color: #f5f6f7;
  font-size: 20px;
  border-radius: 30px;
  border: transparent;
  font-size: 46px;
  font-weight: 400;
  line-height: 1.3;
  font-weight: 500;
  text-align: left;
  color: #caced4;
}

.payDetail-input:focus {
  color: #353b43; /* 입력 필드가 선택되었을 때의 색상 */
  outline: none; /* 포커스 상태에서 라인 없애기 */
}
.payDetail-input::placeholder {
  margin: 0;
  width: 737px;
  height: 138px;
  background-color: #f5f6f7;
  font-size: 20px;
  border-radius: 30px;
  border: transparent;
  font-size: 46px;
  font-weight: 400;
  line-height: 1.3;
  text-align: left;
  color: #caced4;
}

.category {
  width: 965px;
  font-size: 40px;
  gap: 20px;
  margin-top: 85px;
  display: flex;
  flex-direction: column;
}

.category-box {
  width: 100%;
  display: flex;
  justify-content: space-between;
  gap: 35px;
  overflow-x: scroll; /* 수평 스크롤 비활성화 */
  overflow-y: hidden; /* 수직 스크롤 활성화 */
}

.category-box-component {
  width: 138px;
  height: 196px;
  flex-direction: column;
  justify-content: flex-start; /* 변경 */
  align-items: center;
  gap: 10px;
  display: flex;
  flex-shrink: 0;
  margin: 0;
}

.category-box-img {
  width: 138px;
  height: 138px;
  object-fit: cover;
}

.category-box-txt {
  text-align: center;
  color: #caced4;
  font-size: 40px;
  font-weight: 500;
  word-wrap: break-word;
}

.ctabar {
  height: 221.28px;
  margin-top: 500px;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style> -->

<template>
  <div class="payment-box">
    <Modal @close="handleModalClose" @confirm="handleModalConfirm" />
    <Topbar class="topbar" titleText="경비" />
    <TopSelect class="top-select" onetitle="지출 추가" twotitle="경비 추가" @updateType="updateExpenseType" />
    <div class="price-box">
      <div class="price-details">
        <input v-model="displayAmountJPY" @input="updateAmountJPY" type="text" class="print-big-price" placeholder="0 JPY" />
      </div>
      <div class="type-of-money" style="margin-left: 75px">(일본 엔)</div>
      <div class="print-small-price">={{ conversionResult.KRW }} 원</div>
    </div>
    <div class="payType">
      <span class="title">{{ expenseType === '지출' ? '지출 형태' : '추가 형태' }}</span>
      <div class="payType-box">
        <div class="payType-list" :class="{ selected: paymentMethod === '현금' }" @click="selectPaymentMethod('현금')">현금</div>
        <div class="payType-list" :class="{ selected: paymentMethod === '카드' }" @click="selectPaymentMethod('카드')">카드</div>
      </div>
    </div>
    <div class="payDetail">
      <span class="title">{{ expenseType === '지출' ? '지출 내용' : '추가 내용' }}</span>
      <input v-model="expenseDetail" type="text" class="payDetail-input" :placeholder="expenseType === '지출' ? '내용을 입력해 주세요' : '추가 내용을 입력해 주세요'" />
    </div>
    <div class="category">
      <span class="title">카테고리</span>
      <div class="category-box">
        <div class="category-box-component" v-for="category in categories" :key="category" @click="selectCategory(category)" :class="{ selected: selectedCategory === category }">
          <img class="category-box-img" :src="getCategoryImage(category)" style="object-fit: cover" />
          <div class="category-box-txt">{{ category }}</div>
        </div>
      </div>
    </div>
    <div class="ctabar">
      <!-- <CtaBar inputname="등록하기" @click="navigateToHyunsoo" /> -->
      <CtaBarBlack inputname="등록하기" @click="navigateToHyunsoo" />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';
import Topbar from '@/components/Topbar.vue';
// import CtaBar from '@/components/CtaBar.vue';
import CtaBarBlack from '@/components/CtaBarBlack.vue';
import TopSelect from '@/components/TopSelect.vue';
import { useRouter, useRoute } from 'vue-router';
import Modal from '@/components/Modal.vue';

const router = useRouter();
const route = useRoute();

const amountJPY = ref(0);
const displayAmountJPY = ref('');
const conversionResult = ref({ KRW: 0 });
const expenseType = ref('지출');
const paymentMethod = ref('현금');
const expenseDetail = ref('');
const selectedCategory = ref('');

const selectedCountries = ref(route.query.countries ? route.query.countries.split(',') : []);
const selectedDates = ref(route.query.selectedDates ? JSON.parse(route.query.selectedDates) : []);
const memberCount = ref(route.query.memberCount ? parseInt(route.query.memberCount) : 0);
const travelTitle = ref(route.query.travelTitle || '');

const categories = ['관광', '교통', '쇼핑', '숙박', '음식', '항공', '기타'];

const generateUniqueId = () => {
  return Date.now() + Math.floor(Math.random() * 1000);
};

const handleModalClose = (confirm) => {
  if (confirm) {
    registerDefaultData().then(() => {
      router.push({ path: '/hyunsoo' });
    });
  }
};

const handleModalConfirm = () => {
  console.log('Modal confirmed');
};

const registerDefaultData = async () => {
  const expense = {
    id: generateUniqueId(),
    type: expenseType.value,
    paymentMethod: paymentMethod.value,
    description: '기본 등록',
    category: '기타',
    amount: 0,
    convertedAmount: 0,
    date: selectedDates.value[0], // startPeriod 값을 기본 날짜로 사용
  };

  const currentDate = new Date();
  const startDate = new Date(selectedDates.value[0]);
  const endDate = new Date(selectedDates.value[selectedDates.value.length - 1]);

  let daysUntilTrip = Math.ceil((startDate.getTime() - currentDate.getTime()) / (1000 * 60 * 60 * 24));
  if (daysUntilTrip < 0) daysUntilTrip = 0;

  let travelComplete = '다가오는 여행';
  if (currentDate >= startDate && currentDate <= endDate) {
    travelComplete = '여행 중';
  } else if (currentDate > endDate) {
    travelComplete = '완료';
  }

  const userId = '08ac';
  try {
    const response = await axios.get(`http://localhost:3000/users/${userId}`);
    const user = response.data;

    let totalBudget = 0;
    let usedBudget = 0;
    let remainingBudget = 0;
    if (expenseType.value === '추가') {
      totalBudget += expense.convertedAmount;
      remainingBudget += expense.convertedAmount;
    } else {
      totalBudget -= expense.convertedAmount;
      usedBudget += expense.convertedAmount;
    }

    const newTrip = {
      id: generateUniqueId(),
      daysUntilTrip,
      startPeriod: selectedDates.value[0],
      endPeriod: selectedDates.value[selectedDates.value.length - 1],
      category: selectedCategory.value,
      country: selectedCountries.value.join(','),
      type: expenseType.value,
      travelComplete,
      describe: travelTitle.value,
      headcount: memberCount.value,
      expenses: [expense],
      totalBudget,
      usedBudget,
      remainingBudget,
    };

    user.trips.push(newTrip);

    await axios.put(`http://localhost:3000/users/${userId}`, user);
    console.log('Default data registered successfully');
  } catch (error) {
    console.error('Failed to register default data:', error);
  }
};

const navigateToHyunsoo = async () => {
  const expense = {
    id: generateUniqueId(),
    type: expenseType.value,
    paymentMethod: paymentMethod.value,
    description: expenseDetail.value,
    category: selectedCategory.value,
    amount: parseFloat(displayAmountJPY.value.replace(' JPY', '')),
    convertedAmount: parseFloat(conversionResult.value.KRW),
    date: selectedDates.value[0], // startPeriod 값을 기본 날짜로 사용
  };

  const currentDate = new Date();
  const startDate = new Date(selectedDates.value[0]);
  const endDate = new Date(selectedDates.value[selectedDates.value.length - 1]);

  let daysUntilTrip = Math.ceil((startDate.getTime() - currentDate.getTime()) / (1000 * 60 * 60 * 24));
  if (daysUntilTrip < 0) daysUntilTrip = 0;

  let travelComplete = '다가오는 여행';
  if (currentDate >= startDate && currentDate <= endDate) {
    travelComplete = '여행 중';
  } else if (currentDate > endDate) {
    travelComplete = '완료';
  }

  const userId = '08ac';
  try {
    const response = await axios.get(`http://localhost:3000/users/${userId}`);
    const user = response.data;

    let totalBudget = 0;
    let usedBudget = 0;
    let remainingBudget = 0;
    if (expenseType.value === '추가') {
      totalBudget += expense.convertedAmount;
      remainingBudget += expense.convertedAmount;
    } else {
      totalBudget -= expense.convertedAmount;
      usedBudget += expense.convertedAmount;
    }

    const newTrip = {
      id: generateUniqueId(),
      daysUntilTrip,
      startPeriod: selectedDates.value[0],
      endPeriod: selectedDates.value[selectedDates.value.length - 1],
      category: selectedCategory.value,
      country: selectedCountries.value.join(','),
      type: expenseType.value,
      travelComplete,
      describe: travelTitle.value,
      headcount: memberCount.value,
      expenses: [expense],
      totalBudget,
      usedBudget,
      remainingBudget,
    };

    user.trips.push(newTrip);

    await axios.put(`http://localhost:3000/users/${userId}`, user);
    console.log('Trip added successfully');
  } catch (error) {
    console.error('Failed to add trip:', error);
  }

  router.push({
    path: '/hyunsoo',
  });
};

async function fetchExchangeRate(from, to) {
  try {
    const apiKey = 'ffb7b54e74ef452e0961d9d9';
    const proxyUrl = 'https://api.allorigins.win/get?url=';
    const apiUrl = `https://v6.exchangerate-api.com/v6/${apiKey}/pair/${from}/${to}`;
    const response = await fetch(proxyUrl + encodeURIComponent(apiUrl));
    const data = await response.json();
    const parsedData = JSON.parse(data.contents);
    if (parsedData.result === 'success') {
      return parsedData.conversion_rate;
    } else {
      console.error(`Failed to fetch exchange rate for ${from} to ${to}: ${parsedData['error-type']}`);
      return 0;
    }
  } catch (error) {
    console.error(`Failed to fetch exchange rate for ${from} to ${to}:`, error);
    return 0;
  }
}

async function convertCurrency() {
  if (amountJPY.value > 0) {
    const JPYtoKRW = await fetchExchangeRate('JPY', 'KRW');
    conversionResult.value.KRW = (amountJPY.value * JPYtoKRW).toFixed(2);
  } else {
    conversionResult.value.KRW = 0;
  }
}

function updateAmountJPY(event) {
  const value = event.target.value.replace(/\D/g, '');
  amountJPY.value = value ? parseFloat(value) : 0;
  displayAmountJPY.value = value ? `${value} JPY` : '';
  convertCurrency();
}

function selectExpenseType(type) {
  expenseType.value = type;
}

function selectPaymentMethod(method) {
  paymentMethod.value = method;
}

function selectCategory(category) {
  selectedCategory.value = category;
}

function getCategoryImage(category) {
  try {
    return new URL(`/src/assets/${category}.png`, import.meta.url).href;
  } catch (e) {
    return new URL(`/src/assets/default.png`, import.meta.url).href;
  }
}

function updateExpenseType(type) {
  expenseType.value = type;
}
</script>

<style scoped>
.category-box .selected {
  /* background-color: var(--blue-200); 선택된 항목의 배경색 */
  filter: hue-rotate(350deg) brightness(1) saturate(7);
  color: white; /* 선택된 항목의 글자색 */
}
.payment-box {
  width: 1080px;
  height: 2340px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  margin: 0 auto;
  background-color: #fff;
}

.topbar {
  margin-bottom: 20px;
}

.box {
  width: 335px;
  height: 30px;
  padding: 30px;
  margin: 0;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 20px;
  font-size: 35px;
}

.selected {
  background-color: white;
}

.price-box {
  margin-top: 58px;
  margin-bottom: 28px;
  width: 965px;
  height: 288px;
  background-color: #f5f6f7;
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
}

.price-details {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 10px;
  color: #8892a0;
  margin-left: 75px;
}

.print-big-price {
  width: 500px;
  background-color: transparent;
  border: transparent;
  font-size: 80px;
  font-weight: 600;
  line-height: 80px;
  outline: none; /* 클릭 시 테두리가 보이지 않도록 설정 */
}

.type-of-money {
  color: #8892a0;
  font-size: 35px;
  font-weight: 600;
  line-height: 35px;
  word-wrap: break-word;
}

.print-small-price {
  color: #8892a0;
  font-size: 46px;
  font-weight: 600;
  line-height: 46px;
  word-wrap: break-word;
  margin-top: 23px;
  margin-left: 75px;
}

.price-box-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
}

.payInfoBox {
  width: 964.8px;
  padding: 15px;
  border-radius: 20px;
  font-size: 40px;
  gap: 20px;
  background-color: #f5f6f7;
  margin: 25px auto;
}

.payType {
  width: 965px;
  height: 184px;
  border-radius: 20px;
  font-size: 52px;
  padding-bottom: 23.04px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title {
  margin: 0;
  font-weight: 600;
  font-size: 52px;
  line-height: 60px;
  color: #353b 43;
}

.payType-box {
  margin: 0;
  display: flex;
  gap: 30px;
}

.payType-list {
  width: 259px;
  height: 127px;
  border-radius: 30px;
  font-weight: 500;
  line-height: 46.08px;
  color: black;
  background-color: #f5f6f7;
  word-wrap: break-word;
  display: flex;
  align-items: center;
  justify-content: center;
}

.payType .selected {
  background: #616b79;
  color: white;
}

.payDetail {
  width: 965px;
  border-radius: 20px;
  font-size: 40px;
  gap: 20px;
  justify-content: space-between;
  display: flex;
  align-items: center;
}

.payDetail-input {
  margin: 0;
  width: 650px;
  height: 138px;
  padding: 0 40px; /* 텍스트 왼쪽 여백 추가 */
  background-color: #f5f6f7;
  font-size: 20px;
  border-radius: 30px;
  border: transparent;
  font-size: 46px;
  font-weight: 400;
  line-height: 1.3;
  font-weight: 500;
  text-align: left;
  color: #caced4;
}

.payDetail-input:focus {
  color: #353b43; /* 입력 필드가 선택되었을 때의 색상 */
  outline: none; /* 포커스 상태에서 라인 없애기 */
}
.payDetail-input::placeholder {
  margin: 0;
  width: 737px;
  height: 138px;
  background-color: #f5f6f7;
  font-size: 20px;
  border-radius: 30px;
  border: transparent;
  font-size: 46px;
  font-weight: 400;
  line-height: 1.3;
  text-align: left;
  color: #caced4;
}

.category {
  width: 965px;
  font-size: 40px;
  gap: 20px;
  margin-top: 85px;
  display: flex;
  flex-direction: column;
}

.category-box {
  width: 100%;
  display: flex;
  justify-content: space-between;
  gap: 35px;
  padding-top: 12px;
  overflow-x: scroll; /* 수평 스크롤 비활성화 */
  overflow-y: hidden; /* 수직 스크롤 활성화 */
}

.category-box-component {
  width: 138px;
  height: 196px;
  flex-direction: column;
  justify-content: flex-start; /* 변경 */
  align-items: center;
  gap: 10px;
  display: flex;
  flex-shrink: 0;
  margin: 0;
}

.category-box-img {
  width: 138px;
  height: 138px;
  object-fit: cover;
}

.category-box-txt {
  text-align: center;
  color: #caced4;
  font-size: 40px;
  font-weight: 500;
  word-wrap: break-word;
}

.ctabar {
  height: 221.28px;
  margin-top: 500px;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
