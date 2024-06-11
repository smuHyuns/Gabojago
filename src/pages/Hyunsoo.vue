<template>
  <div class="BlueBox">
    <Header class="header" />
    <div class="imgContainer">
      <img src="../assets/비행기토끼.png" class="rabbitImg" />
    </div>
    <InfoBox class="infoBox" firstInput="돈 관리는 저희가 도와드릴게요" secondInput="어디로 떠나시나요?" thirdInput="여행 등록하기 >" />
  </div>
  <div class="whiteBox">
    <div class="kindTripBox">
      <ul>
        <li class="tripCategory selected">
          <div class="tripCategoryName">전체</div>
        </li>
        <li class="tripCategory">
          <div class="tripCategoryName">여행 중</div>
          <div class="tripCategoryCount">0</div>
        </li>
        <li class="tripCategory">
          <div class="tripCategoryName">다가오는 여행</div>
          <div class="tripCategoryCount">0</div>
        </li>
        <li class="tripCategory">
          <div class="tripCategoryName">지난 여행</div>
          <div class="tripCategoryCount">0</div>
        </li>
      </ul>
    </div>
    <div class="planListBox">
      <div class="noneBox">여행 중인 곳이 없어요<br />여행을 등록해주세요</div>
    </div>
    <img src="../assets/addPlanBtn.png" class="addPlanBtn" />
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
  margin-bottom: 0; /* 잔여 공간이 없도록 설정 */
  padding-bottom: 0; /* 추가된 여백 제거 */
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
  height: 430px;
  bottom: 0; /* imgContainer의 밑부분을 완전히 덮도록 설정 */
  width: 100%; /* infoBox가 BlueBox의 전체 너비를 차지하도록 설정 */
  margin: 0;
  background: linear-gradient(180deg, rgba(123, 160, 255, 0) 0%, #749bff 36%, #5d8bff 100%);
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
  justify-content: flex-start; /* 좌측 정렬 */
  margin: 30px;
}

.kindTripBox ul {
  display: flex;
  gap: 20px; /* li 요소 간의 간격을 조금 더 키움 */
  list-style: none; /* 기본 리스트 스타일 제거 */
  padding: 0; /* 기본 패딩 제거 */
  margin: 0; /* 기본 마진 제거 */
}

/* trip category */
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
}

.tripCategory:hover {
  background-color: var(--grey-700); /* 선택된 배경 색상 */
  color: white !important; /* 선택된 글자 색상 */
  transition-duration: 0.8s;
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

.selected {
  background-color: var(--grey-700); /* 선택된 배경 색상 */
  color: white !important; /* 선택된 글자 색상 */
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
  width: 200px;
  height: 200px;
  position: absolute;
  right: 50px; /* 오른쪽 50px 위치 */
  bottom: 100px; /* 아래쪽 100px 위치 */
  border: none;
  cursor: pointer;
}
</style>
