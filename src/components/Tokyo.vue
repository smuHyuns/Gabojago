<template>
  <div class="year">
    <div class="box">
      <div class="date">2024년 06월 15일</div>
    </div>
  </div>
  <div class="card">
    <div class="box2">
      <div class="rect">
        <div class="tour"></div>
        <div class="vector"></div>
      </div>
    </div>
  </div>
  <div class="left">
    관광
    <div class="group">
      <div class="jpy">- 580 JPY</div>
      <div class="kor">- 5,109원</div>
    </div>
  </div>
</template>

<script setup></script>

<style scoped>
.year {
  width: 506.25px;
  height: 43.2px;
  position: relative;
}
.box {
  width: 506.25px;
  height: 43.2px;
  left: 0px;
  top: 0px;
  position: absolute;
  background: #f5f6f7;
}
.date {
  left: 27px;
  top: 12.15px;
  position: absolute;
  color: #8892a0;
  font-size: 18.9px;

  font-weight: 500;
  line-height: 18.9px;
  word-wrap: break-word;
}
.card {
  width: 506.25px;
  height: 101.25px;
  position: relative;
}
.box2 {
  width: 64.8px;
  height: 64.8px;
  left: 0px;
  top: 0px;
  position: absolute;
  background: #f5f6f7;
  border-radius: 13.5px;
}
.tour {
  width: 32.4px;
  height: 32.4px;
  padding-top: 2.7px;
  padding-bottom: 2.82px;
  padding-left: 4.05px;
  padding-right: 2.7px;
  left: 15.8px;
  top: 16.2px;
  position: absolute;
  justify-content: center;
  align-items: center;
  display: inline-flex;
}
.vector {
  width: 25.65px;
  height: 26.88px;
  background: #caced4;
}
.left {
  left: 108px;
  top: 37.8px;
  position: absolute;
  color: black;
  font-size: 24.3px;

  font-weight: 600;
  line-height: 24.3px;
  word-wrap: break-word;
}
.group {
  left: 803.52px;
  top: 54.72px;
  position: absolute;
}
.jpy {
  left: 402.3px;
  top: 63.45px;
  position: absolute;
  text-align: right;
  color: #8892a0;
  font-size: 16.2px;

  font-weight: 500;
  line-height: 16.2px;
  word-wrap: break-word;
}
.kor {
  left: 0px;
  top: 0px;
  position: absolute;
  text-align: right;
  color: #353b43;
  font-size: 51.84px;

  font-weight: 600;
  line-height: 51.84px;
  word-wrap: break-word;
}
</style>
