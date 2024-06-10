<template>
  <div class="BlueBox">
    <Header class="header" />
    <div class="imgContainer">
      <img src="../assets/비행기토끼.png" class="rabbitImg" />
    </div>
    <InfoBox
      class="infoBox"
      firstInput="돈 관리는 저희가 도와드릴게요"
      secondInput="어디로 떠나시나요?"
      thirdInput="여행 등록하기 >"
    />
  </div>
  <div class="whiteBox">
    <div class="kindTripBox">
      <ul>
        <li class="selected">전체</li>
        <li>여행중</li>
        <li>다가오는여행</li>
        <li>지난 여행</li>
      </ul>
    </div>
    <div class="planListBox">
      <div class="noneBox">
        여행 중인 곳이 없습니다<br />여행을 등록해주세요
      </div>
    </div>
    <button class="addPlanBtn">
      <div class="cross"></div>
    </button>
  </div>
</template>

<script setup>
import Header from '@/components/Header.vue';
import InfoBox from '@/components/InfoBox.vue';
</script>

<style scoped>
.BlueBox {
  height: 1170px;
  width: 1080px;
  margin: 0;
  background-color: var(--blue-200);
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: hidden; /* 이미지가 박스를 넘지 않도록 설정 */
}

.imgContainer {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-grow: 1;
  position: relative;
}

.rabbitImg {
  max-width: 100%;
  max-height: calc(100% - 220px); /* 헤더와의 거리 포함 */
  width: auto;
  height: auto;
  object-fit: contain; /* 비율을 유지하면서 이미지가 커지거나 작아지도록 함 */
  transform: scale(1.1); /* 이미지 크기를 1.1배로 확대 */
  margin-top: 20px;
}

.header {
  position: relative;
  top: 170px;
}

.infoBox {
  position: absolute;
  bottom: 20px; /* imgContainer의 밑부분을 살짝 덮도록 설정 */
  box-shadow: 0 -10px 10px -10px rgba(0, 0, 0, 0.5); /* 상단에 그림자 효과 추가 */
}

.whiteBox {
  height: 1160px;
  width: 1080px;
  background-color: var(--grey-0);
  margin: 0;
  position: relative; /* 버튼 위치 설정을 위한 기준 요소 */
}

.kindTripBox {
  max-width: 1080px;
  height: 100px;
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px;
  overflow-x: auto; /* 내용이 넘칠 경우 수평 스크롤 */
  justify-content: space-between;
  margin: 30px;
}

.kindTripBox ul {
  display: flex;
  gap: 10px; /* li 요소 간의 간격 */
  list-style: none; /* 기본 리스트 스타일 제거 */
  padding: 0; /* 기본 패딩 제거 */
  margin: 0; /* 기본 마진 제거 */
}

.kindTripBox ul li {
  flex-direction: column;
  padding: 22px 30px;
  border-radius: 55px;
  color: black;
  font-size: 35px;
  font-weight: bold;
  margin-left: 20px;
  flex-shrink: 0; /* li 요소가 수평 방향으로 압축되지 않도록 설정 */
  transition: background-color 0.5s ease, color 0.5s ease; /* 천천히 변화하는 효과 추가 */
}

li.selected {
  background-color: var(--grey-700);
  color: white !important;
}

ul li {
  background-color: var(--grey-200);
  color: var(--grey-700) !important;
}

ul li:hover {
  background-color: var(--grey-700);
  color: white !important;
}

.planListBox {
  width: 900px;
  height: 900px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.noneBox {
  font-size: 40px;
  text-align: center;
  color: var(--grey-400);
}

.addPlanBtn {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  background-color: var(--grey-700); /* 원형 버튼의 배경 색상 */
  display: flex;
  align-items: center;
  justify-content: center;
  position: absolute;
  right: 50px; /* 오른쪽 50px 위치 */
  bottom: 100px; /* 아래쪽 100px 위치 */
  border: none;
  cursor: pointer;
}

.addPlanBtn .cross {
  width: 60px; /* 십자가의 너비 */
  height: 60px; /* 십자가의 높이 */
  position: relative;
}

.addPlanBtn .cross::before,
.addPlanBtn .cross::after {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 50px; /* 십자가의 가로선 길이 */
  height: 10px; /* 십자가의 가로선 두께 */
  background-color: white; /* 십자가 색상 */
  transform: translate(-50%, -50%);
}

.addPlanBtn .cross::before {
  transform: translate(-50%, -50%) rotate(90deg);
}
</style>
