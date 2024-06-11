<template>
  <div id="app">
    <footer class="footer">
      <div class="container">
        <div class="row">
          <div class="col text-left">
            <img src="../assets/가계부.png" alt="가계부" />
          </div>
          <div class="col text-right">
            <img src="../assets/리포트.png" alt="리포트" />
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  name: "App",
};
</script>

<style>
/* CSS 코드는 부트스트랩을 사용하더라도 적용될 수 있도록 작성하였습니다. */
.footer {
  position: fixed;
  bottom: 0;
  width: 100%;
  background-color: #f8f9fa;
  padding: 10px 0;
}
</style>
