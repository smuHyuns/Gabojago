<template>
  <div>
    <TopbarWithIcontokyo titleText="일주일 도쿄여행" class="topbar" />
  </div>
  <div>
    <HistoryListItem />
  </div>
  <div>
    <CtaBarSiwan />
  </div>
  <div>
    <CtaBarBlackSiwan class="ctabarblacksiwan" inputname="추가하기" />
  </div>
</template>

<script setup>
import TopbarWithIcontokyo from '@/components/Trip/TopbarWithIcon-tokyo.vue';
import HistoryListItem from '@/components/HistoryListItem.vue';
import CtaBarSiwan from '@/components/CtaBar-siwan.vue';
import CtaBarBlackSiwan from '@/components/CtaBarBlack-siwan.vue';
</script>

<style scoped></style>
