<template>
  <div class="header">
    <div class="logoBox"></div>
    <div class="icon-container">
      <i class="fa-solid fa-user"></i>
    </div>
  </div>
</template>

<script setup></script>

<style scoped>
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding: 0;
}

.logoBox {
  width: 200px;
  height: 100px;
  border: 1px solid black;
  margin-left: 75px;
  background-image: url('https://www.logoyogo.com/web/wp-content/uploads/edd/2021/02/logoyogo-1-78.jpg');
  background-size: cover;
  background-position: center;
}

.icon-container {
  display: flex;
  justify-content: flex-end;
  flex-grow: 1;
}

.fa-solid {
  font-size: 3rem;
  margin-right: 75px;
  color: white;
}
</style>
