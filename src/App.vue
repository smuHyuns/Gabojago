<template>
  <div>
    <h1>App화면</h1>
    <hr />
    <h3><router-link to="/">home 이동</router-link></h3>
    <h3><router-link to="/isu">isu 이동</router-link></h3>
    <h3><router-link to="/gun">gun 이동</router-link></h3>
    <h3><router-link to="/siwan">siwan 이동</router-link></h3>
    <h3><router-link to="/siwan_test">siwan_test 이동</router-link></h3>
    <h3><router-link to="/hyunsoo">hyunsoo 이동</router-link></h3>

    <h3><router-link to="/addPayment">addPayment 이동</router-link></h3>
    <h3><router-link to="/TravleName">TravleName 이동</router-link></h3>
    <h3><router-link to="/Member">Member 이동</router-link></h3>
    <h3><router-link to="/Err">Err 이동</router-link></h3>
    <h3><router-link to="/calendar">캘린더 이동</router-link></h3>
    <h3><router-link to="/modal">modal-test</router-link></h3>
    <h3><router-link to="/Start">시작페이지</router-link></h3>
    <hr />

    &nbsp;
    <div class="mainContainer">
      <div class="container">
        <RouterView></RouterView>
      </div>
    </div>
  </div>
</template>

<script setup></script>

<style>
* {
  margin: 0px auto;
  padding: 0px;
  font-family: "Pretendard-Regular";
}

#app {
  margin: 0;
}

@font-face {
  font-family: "Pretendard-Regular";
  src: url("https://fastly.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff")
    format("woff");
  font-weight: 400;
  font-style: normal;
}

.mainContainer {
  max-width: 1080px;
  max-height: 2340px;
  border: 1px solid black;
  background-color: white;
}

.container {
  width: 1080px;
  height: 2340px;
}
</style>
