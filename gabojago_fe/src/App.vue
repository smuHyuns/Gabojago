<template>
  <div>
    <div class="mainContainer">
      <div class="container">
        <RouterView></RouterView>
      </div>
    </div>
  </div>
</template>

<script setup></script>

<style>
@import url('https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/variable/pretendardvariable-dynamic-subset.min.css');

* {
  margin: 0px auto;
  padding: 0px;
  font-family: 'Pretendard Variable', Pretendard, -apple-system,
    BlinkMacSystemFont, system-ui, Roboto, 'Helvetica Neue', 'Segoe UI',
    'Apple SD Gothic Neo', 'Noto Sans KR', 'Malgun Gothic', 'Apple Color Emoji',
    'Segoe UI Emoji', 'Segoe UI Symbol', sans-serif;
}
::-webkit-scrollbar {
  background-color: transparent;
}

/* #app {
  margin: 0;
} */

@font-face {
  font-family: 'Pretendard-Regular';
  src: url('https://fastly.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff')
    format('woff');
  font-weight: 400;
  font-style: normal;
}

/*  */
html,
body {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  overflow: hidden; /* 스크롤 방지 */
}

#app {
  width: 100%;
  height: 100%;
  margin: 0;
}

.mainContainer {
  width: 100vw; /* 뷰포트 너비를 100% 사용 */
  height: 100vh; /* 뷰포트 높이를 100% 사용 */
  background-color: white;
  display: flex;
  justify-content: center; /* 중앙 정렬 */
  align-items: center; /* 중앙 정렬 */
}

.container {
  width: 100%; /* 부모 요소 크기에 맞춤 */
  height: 100%; /* 부모 요소 크기에 맞춤 */
}

/* .mainContainer {
  max-width: 1080px;
  max-height: 2340px;
  background-color: white;
}

.container {
  width: 1080px;
  height: 2340px;
} */
</style>
