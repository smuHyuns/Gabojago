<template>
  <div class="payment-box">
    <Topbar class="topbar" titleText="공동 경비" />
    <div class="tab-bar">
      <div class="box">지출 추가</div>
      <div class="box selected">경비 추가</div>
    </div>
    <div class="price-box">
      <div class="price-details">
        <div class="print-big-price">0JPY<span class="type-of-money">(일본 엔)</span></div>
        <!-- <div class="type-of-money">(일본 엔)</div> -->
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
      <input
        type="text"
        class="payDetail-input"
        placeholder=" 내용을 입력해 주세요"
      />
    </div>
    <div class="category">
      <span class="title">카테고리</span>
      <div class="category-box">
        <div class="category-box-component">
          <img class="category-box-img" src="../assets/비행기토끼.png"></img>
          <div class="category-box-txt">여행</div>
        </div>
        <div class="category-box-component">
          <img class="category-box-img" src="../assets/비행기토끼.png"></img>
          <div class="category-box-txt">여행</div>
        </div>
        <div class="category-box-component">
          <img class="category-box-img" src="../assets/비행기토끼.png"></img>
          <div class="category-box-txt">여행</div>
        </div>
        <div class="category-box-component">
          <img class="category-box-img" src="../assets/비행기토끼.png"></img>
          <div class="category-box-txt">여행</div>
        </div>
        <div class="category-box-component">
          <img class="category-box-img" src="../assets/비행기토끼.png"></img>
          <div class="category-box-txt">여행</div>
        </div>
        <div class="category-box-component">
          <img class="category-box-img" src="../assets/비행기토끼.png"></img>
          <div class="category-box-txt">여행</div>
        </div>
      </div>
    </div>

    <CtaBar inputname="등록하기" />
    <!-- 다른 컴포넌트나 요소들을 여기에 추가하세요 -->
  </div>
</template>

<script setup>
import Topbar from '@/components/Topbar.vue';
import CtaBar from '@/components/CtaBar.vue';
</script>

<style scoped>
.payment-box {
  width: 1080px;
  height: 2340px;
  max-width: 1080px;
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
  margin: 50px;
  width: 964.8px;
  padding: 15px;
  height: 250px;
  background-color: #f5f6f7;
  /* border: 1px solid black; */
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start; /* This centers the box itself */
}

.price-details {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 10px;
  color: #8892a0;
  margin-left: 30px;
}

.print-big-price {
  font-size: 80.64px;
  font-weight: 700;
  line-height: 80.64px;
}

.type-of-money {
  color: #8892a0;
  font-size: 34.56px;
  font-weight: 600;
  line-height: 34.56px;
  word-wrap: break-word;
}

.print-small-price {
  color: #8892a0;
  font-size: 46.08px;
  font-weight: 600;
  line-height: 46.08px;
  word-wrap: break-word;
  margin-left: 30px;
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
  height: 184.32px;
  border-radius: 20px;
  font-size: 40px;
  /* gap: 20px; */
  /* border: 1px solid black; */
  padding-bottom: 23.04px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title {
  margin: 0;
  font-weight: bold;
  font-size: 46.08px;
  font-weight: 500;
  line-height: 59.9px;
}

.payType-box {
  margin: 0;
  display: flex;
  gap: 20px;
}

.payType-list {
  width: 259.2px;
  height: 126.72px;
  border-radius: 20px;
  font-weight: 600;
  line-height: 46.08px;
  color: black;
  background-color: #f5f6f7;
  word-wrap: break-word;
  display: flex; /* 추가 */
  align-items: center; /* 추가 */
  justify-content: center; /* 추가 */
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
  width: 737.28px;
  height: 138.24px;
  background-color: #f5f6f7;
  font-size: 20px;
  border-radius: 28.8px;
  border: transparent;
  color: #caced4;
  font-size: 46.08px;
  font-weight: 500;
  line-height: 59.9px;
}

.category {
  width: 964.8px;
  padding: 15px;
  border-radius: 20px;
  font-size: 40px;
  gap: 20px;
  /* border: 1px solid black; */
  margin: 25px auto;
  display: flex;
  flex-direction: column;
}

.category-box {
  width: 100%;
  /* align-self: stretch; */
  /* border: 1px solid black; */
  display: flex;
  justify-content: space-between;
  gap: 30px;
}

.category-box-component {
  width: 138.24px;
  height: 195.84px;
  flex-direction: column;
  justify-content: flex-start; /* 변경 */
  align-items: center;
  gap: 10px;
  display: flex;
  margin: 0;
}

.category-box-img {
  width: 138.24px;
  height: 138.24px;
  object-fit: cover;
  border: 1px solid black;
}

.category-box-txt {
  text-align: center;
  color: #caced4;
  font-size: 40.32px;
  font-weight: 700;
  line-height: 40.32px;
  word-wrap: break-word;
}
</style>
