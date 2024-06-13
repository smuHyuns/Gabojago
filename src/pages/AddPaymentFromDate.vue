<template>
  <div class="payment-box">
    <Topbar class="topbar" titleText="경비" />
    <TopSelect class="top-select" onetitle="지출 추가" twotitle="경비 추가" />
    <div class="price-box">
      <div class="price-details">
        <input
          v-model="displayAmountJPY"
          @input="updateAmountJPY"
          type="text"
          class="print-big-price"
          placeholder="0 JPY"
        />
      </div>
      <div class="type-of-money" style="margin-left: 75px">(일본 엔)</div>
      <div class="print-small-price">={{ conversionResult.KRW }} 원</div>
    </div>
    <div class="payType">
      <span class="title">지출 형태</span>
      <div class="payType-box">
        <div
          class="payType-list"
          :class="{ selected: paymentMethod === '현금' }"
          @click="selectPaymentMethod('현금')"
        >
          현금
        </div>
        <div
          class="payType-list"
          :class="{ selected: paymentMethod === '카드' }"
          @click="selectPaymentMethod('카드')"
        >
          카드
        </div>
      </div>
    </div>
    <div class="payDetail">
      <span class="title">지출 내용</span>
      <input
        v-model="expenseDetail"
        type="text"
        class="payDetail-input"
        placeholder="내용을 입력해 주세요"
      />
    </div>
    <div class="category">
      <span class="title">카테고리</span>
      <div class="category-box">
        <div
          class="category-box-component"
          v-for="category in categories"
          :key="category"
          @click="selectCategory(category)"
          :class="{ selected: selectedCategory === category }"
        >
          <img
            class="category-box-img"
            :src="getCategoryImage(category)"
            style="object-fit: cover"
          />
          <div class="category-box-txt">{{ category }}</div>
        </div>
      </div>
    </div>
    <div class="ctabar">
      <CtaBar inputname="등록하기" @click="registerExpense" />
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
const categories = ['관광', '교통', '쇼핑', '숙박', '음식', '항공', '기타'];

const selectedDate = ref(route.query.selectedDate || '');
const tripId = ref(route.query.tripId || '');

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

const registerExpense = async () => {
  const expense = {
    id: generateUniqueId(),
    type: expenseType.value,
    paymentMethod: paymentMethod.value,
    description: expenseDetail.value,
    category: selectedCategory.value,
    amount: parseFloat(displayAmountJPY.value.replace(' JPY', '')),
    convertedAmount: parseFloat(conversionResult.value.KRW),
    date: selectedDate.value,
  };

  const userId = '08ac';
  try {
    const response = await axios.get(`http://localhost:3000/users/${userId}`);
    const user = response.data;

    const trip = user.trips.find((t) => t.id === parseInt(tripId.value));
    if (trip) {
      trip.expenses.push(expense);
      if (expenseType.value === '추가') {
        trip.totalBudget += expense.convertedAmount;
        trip.remainingBudget += expense.convertedAmount;
      } else {
        trip.totalBudget -= expense.convertedAmount;
        trip.usedBudget += expense.convertedAmount;
      }

      await axios.put(`http://localhost:3000/users/${userId}`, user);
      console.log('Expense registered successfully');

      // 쿼리 정보를 포함하여 siwan_test 페이지로 이동
      router.push({
        name: 'siwan_test',
        params: { date: selectedDate.value, tripId: tripId.value },
      });
    }
  } catch (error) {
    console.error('Failed to register expense:', error);
  }
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
      console.error(
        `Failed to fetch exchange rate for ${from} to ${to}: ${parsedData['error-type']}`
      );
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
</style>
