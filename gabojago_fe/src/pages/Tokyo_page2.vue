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
import TopbarWithIcontokyo from '@/components/Trip/TopbarWithIcon_delete.vue';
import HistoryListItem from '@/components/compo/HistoryListItem.vue';
import CtaBarSiwan from '@/components/compo/CtaBar-siwan.vue';
import CtaBarBlackSiwan from '@/components/compo/CtaBarBlack-siwan.vue';
</script>

<style scoped></style>
