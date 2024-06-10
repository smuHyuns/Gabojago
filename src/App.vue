<template>
  <div>
    <h1>App화면</h1>
    <hr />
    <h3><router-link to="/">home 이동</router-link></h3>
    <h3><router-link to="/isu">isu 이동</router-link></h3>
    <h3><router-link to="/gun">gun 이동</router-link></h3>
    <h3><router-link to="/siwan">siwan 이동</router-link></h3>
    <h3><router-link to="/hyunsoo">hyunsoo 이동</router-link></h3>
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
