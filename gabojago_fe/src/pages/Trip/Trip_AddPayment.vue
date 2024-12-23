<template>
  <div class="viewport">
    <div class="payment-box">
      <Modal @close="handleModalClose" @confirm="handleModalConfirm" />
      <Topbar class="topbar" titleText="경비" />
      <TopSelect
        class="top-select"
        onetitle="지출 추가"
        twotitle="경비 추가"
        @updateType="updateExpenseType"
      />
      <div class="price-box">
        <div class="price-details">
          <!-- 외화 입력 필드 -->
          <input
            v-model="displayBudget"
            @input="updateBudget"
            type="text"
            class="print-big-price"
            :placeholder="`0 ${currency}`"
          />
        </div>
        <div class="type-of-money" style="margin-left: 75px">{{}}</div>
        <!-- KRW 환전 값 표시 -->
        <div class="print-small-price">{{ conversionResult.KRW }} 원</div>
      </div>
      <div class="payType">
        <span class="title">{{
          expenseType === '지출' ? '지출 형태' : '추가 형태'
        }}</span>
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
        <span class="title">{{
          expenseType === '지출' ? '지출 내용' : '추가 내용'
        }}</span>
        <input
          v-model="expenseDetail"
          type="text"
          class="payDetail-input"
          :placeholder="
            expenseType === '지출'
              ? '내용을 입력해 주세요'
              : '추가 내용을 입력해 주세요'
          "
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
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import Topbar from '@/components/compo/Topbar.vue';
import CtaBar from '@/components/compo/CtaBar.vue';
import TopSelect from '@/components/compo/TopSelect.vue';
import { useRouter, useRoute } from 'vue-router';
import { getCurrency, postTransaction } from '@/api/transaction';
import Modal from '@/components/compo/Modal.vue';
import { useAddTripStore } from '@/stores/tripStore';

const router = useRouter();
const route = useRoute();

const Budget = ref(0); // 외화 입력 값
const displayBudget = ref(''); // 외화 입력 표시값
const conversionResult = ref({ KRW: 0 }); // 환전 결과
const expenseType = ref('지출');
const paymentMethod = ref('현금');
const expenseDetail = ref('');
const selectedCategory = ref('');
const categories = ['관광', '교통', '쇼핑', '숙박', '음식', '항공', '기타'];
const exchangeRate = ref('');
const currency = ref('');
const tripStore = useAddTripStore();

const selectedDate = ref(route.query.date || '');

const handleModalClose = (confirm) => {
  if (confirm) {
    tripStore.clearTripData();
    router.push({ path: '/dashboard' });
  } else {
    console.log('모달이 닫히고 남습니다.');
  }
};

const handleModalConfirm = () => {
  console.log('Modal confirmed');
};

const getRateAndCurrency = async () => {
  try {
    console.log('tripId는 : ', tripStore.tripId);

    const response = await getCurrency(tripStore.tripId);
    currency.value = response.currency;
    exchangeRate.value = response.rate;
    console.log('currency : ', response.currency);
    console.log('exchangeRate : ', response.rate);
    console.log('선택일자 : ', selectedDate.value);
    convertCurrency();
  } catch (error) {
    console.log('페이지에서 환율, 통화단위 불러오기 실패! : ', error);
  }
};

const registerExpense = async () => {
  const request = {
    tripId: tripStore.tripId,
    paymentMethod: paymentMethod.value === '현금' ? 0 : 1,
    transactionType: expenseType.value,
    expenseDate: tripStore.start_period,
    expenseDetail: expenseDetail.value,
    expenseAmount: parseFloat(conversionResult.value.KRW),
    exchangeAmount: parseFloat(displayBudget.value.replace(/\D/g, '')) || 0,
    expenseType: selectedCategory.value,
  };

  console.log('request : ', request);

  try {
    const response = await postTransaction(request);
    console.log('거래 등록 성공:', response);
  } catch (error) {
    console.error('Failed to register expense:', error);
  }

  router.push('/dashbaord');
};

async function convertCurrency() {
  if (displayBudget.value && exchangeRate.value) {
    const inputAmount = parseFloat(displayBudget.value.replace(/\D/g, '')) || 0;
    const exchangeRateToKRW = 1 / parseFloat(exchangeRate.value);
    //conversionResult.value.KRW = (inputAmount * exchangeRateToKRW).toFixed(2);
    conversionResult.value.KRW = (inputAmount / exchangeRate.value).toFixed(2);
  } else {
    conversionResult.value.KRW = 0;
  }
}

function updateBudget(event) {
  const value = event.target.value.replace(/\D/g, '');
  displayBudget.value = value ? `${value} ${currency.value}` : '';
  convertCurrency();
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
  console.log(expenseType.value);
}

// 컴포넌트 로드 시 여행 정보 가져오기
onMounted(() => {
  getRateAndCurrency();
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
  justify-content: center; /* 요소를 중앙에 배치 */
  margin: 0 auto;
  background-color: #fff;
  position: relative;
  border: 1px solid black;
}

.category-box .selected {
  filter: hue-rotate(371deg) brightness(1) saturate(7);
  color: white;
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
  /* display: flex; */
  flex-direction: row;
  /* align-items: center; */
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
  padding: 0 40px;
  background-color: #f5f6f7;
  font-size: 20px;
  border-radius: 30px;
  border: transparent;
  font-size: 46px;
  font-weight: 400;
  line-height: 1.3;
  font-weight: 500;
  text-align: left;
  color: #353b43;
}

.payDetail-input:focus {
  color: #353b43;
  outline: none;
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
  overflow-x: scroll;
  overflow-y: hidden;
}

.category-box-component {
  width: 138px;
  height: 196px;
  flex-direction: column;
  justify-content: flex-start;
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
