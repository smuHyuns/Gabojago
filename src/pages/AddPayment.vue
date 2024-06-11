<template>
  <div class="payment-box">
    <Topbar class="topbar" titleText="공동 경비" />
    <div class="tab-bar">
      <div class="box">지출 추가</div>
      <div class="box selected">경비 추가</div>
    </div>
    <div class="price-box">
      <div class="price-details">
        <div class="print-big-price">0JPY</div>
        <div class="type-of-money">(일본 엔)</div>
      </div>
      <div class="print-small-price">=0원</div>
    </div>
    <div class="payType">
      <span class="title">지출 형태</span>
      <div class="payType-box">
        <div class="payType-list selected">현금</div>
        <div class="payType-list">카드</div>
      </div>
    </div>
    <div class="payDetail">
      <span class="title">지출 내용</span>
      <input type="text" class="payDetail-input" />
    </div>
    <div class="category"><span class="title">카테고리</span></div>

    <CtaBar inputname="등록하기" />
    <!-- 다른 컴포넌트나 요소들을 여기에 추가하세요 -->
  </div>
</template>

<script setup>
import Topbar from '@/components/Topbar.vue';
import CtaBar from '@/components/CtaBar.vue';
</script>

<style scoped>
.temp {
}

.payment-box {
  width: 1080px;
  height: 2340px;
  margin: 0;
  padding: 0;
}

.topbar {
  margin-bottom: 20px;
}

.payment-box .tab-bar {
  width: 964.8px;

  display: flex;
  padding: 15px;
  border-radius: 20px;
  justify-content: center;
  gap: 20px;
  background-color: #f5f6f7;
  margin: 0 auto;
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
  /* background-color: var(--grey-0); */
}

.selected {
  background-color: white;
}

.price-box {
  margin: 30px auto;
  width: 964.8px;

  padding: 15px;
  height: 250px;
  background-color: #f5f6f7;
  /* border: 1px solid black; */
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center; /* This centers the box itself */
}

.price-details {
  display: flex;
  align-items: center;
  gap: 10px;
}

.print-big-price {
  font-size: 50px;
}

.type-of-money {
  font-size: 20px;
}

.print-small-price {
  font-size: 30px;
}

.price-box-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start; /* This aligns the content to the left */
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
  width: 964.8px;
  border-radius: 20px;
  font-size: 40px;
  /* gap: 20px; */
  /* border: 1px solid black; */
  padding: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title {
  margin: 0;
  font-weight: bold;
}

.payType-title {
}

.payType-box {
  margin: 0;
  display: flex;
  gap: 20px;
}

.payType-list {
  width: 150px;
  border-radius: 20px;
  padding: 20px;
  font-size: 30px;
  text-align: center;
  color: black;
  background-color: #f5f6f7;
  word-wrap: break-word;
}

.payType .selected {
  background: #616b79;
  color: white;
}

.payDetail {
  width: 964.8px;
  padding: 15px;
  border-radius: 20px;
  font-size: 40px;
  gap: 20px;
  /* border: 1px solid black; */
  margin: 25px auto;
  justify-content: space-between;
  display: flex;
  align-items: center;
}

.payDetail-input {
  margin: 0;
  width: 500px;
  background-color: #f5f6f7;
  padding: 20px;
  font-size: 20px;
  border-radius: 10px;
  border: transparent;
}

.category {
  width: 964.8px;

  padding: 15px;
  border-radius: 20px;
  font-size: 40px;
  gap: 20px;
  /* border: 1px solid black; */
  margin: 25px auto;
  justify-content: space-between;
  display: flex;
  align-items: center;
}
</style>
