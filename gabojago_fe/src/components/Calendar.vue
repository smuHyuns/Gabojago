<template>
  <div class="calendar-container">
    <div class="calendar-header">
      <img
        src="../assets/chevron-right.png"
        @click="prevMonth"
        class="left-btn"
      />
      <h2>{{ currentMonthYear }}</h2>
      <img
        src="../assets/chevron-left.png"
        @click="nextMonth"
        class="right-btn"
      />
    </div>
    <div class="calendar-days">
      <div class="day" v-for="day in daysOfWeek" :key="day">{{ day }}</div>
    </div>
    <div class="calendar-dates">
      <div
        v-for="date in calendarDates"
        :key="date.key"
        :class="[
          'date',
          {
            empty: date.empty,
            highlight: highlightDates.includes(date.key),
            today: date.key === todayKey,
            selected: date.key === selectedDateKey,
          },
        ]"
        @click="dateClick(date)"
      >
        {{ date.value }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue';

const props = defineProps({
  startPeriod: { type: String, required: true },
  endPeriod: { type: String, required: true },
  tripId: { type: Number, required: true },
});

const emit = defineEmits(['date-click']); // 부모에게 날짜 전달

const today = new Date();
const currentMonth = ref(today.getMonth());
const currentYear = ref(today.getFullYear());
const todayKey = ref(`date-${today.getDate()}`); // 오늘 날짜
const selectedDateKey = ref(null); // 선택된 날짜의 키

const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];
const currentMonthYear = computed(
  () => `${currentYear.value}년 ${currentMonth.value + 1}월`
);
const calendarDates = ref([]);
const highlightDates = ref([]);

function renderCalendar() {
  const firstDayOfMonth = new Date(currentYear.value, currentMonth.value, 1);
  const daysInMonth = new Date(
    currentYear.value,
    currentMonth.value + 1,
    0
  ).getDate();
  const startDayOfWeek = firstDayOfMonth.getDay();

  const dates = [];

  for (let i = 0; i < startDayOfWeek; i++) {
    dates.push({ key: `empty-${i}`, value: '', empty: true });
  }

  for (let i = 1; i <= daysInMonth; i++) {
    dates.push({ key: `date-${i}`, value: i, empty: false });
  }

  calendarDates.value = dates;

  // 오늘 날짜가 현재 달에 해당하는지 확인
  const isCurrentMonth =
    today.getFullYear() === currentYear.value &&
    today.getMonth() === currentMonth.value;
  todayKey.value = isCurrentMonth ? `date-${today.getDate()}` : null;
}

function prevMonth() {
  currentMonth.value--;
  if (currentMonth.value < 0) {
    currentMonth.value = 11;
    currentYear.value--;
  }
  renderCalendar();
}

function nextMonth() {
  currentMonth.value++;
  if (currentMonth.value > 11) {
    currentMonth.value = 0;
    currentYear.value++;
  }
  renderCalendar();
}

function dateClick(date) {
  if (!date.empty) {
    selectedDateKey.value = date.key; // 선택된 날짜 업데이트
    const selectedDate = new Date(
      currentYear.value,
      currentMonth.value,
      date.value
    );
    console.log(selectedDate.toISOString().split('T')[0]);
    emit('date-click', selectedDate.toISOString().split('T')[0]); // 부모로 날짜 전달
  }
}

onMounted(() => {
  renderCalendar();
});

watch([currentMonth, currentYear], renderCalendar);
</script>

<style scoped>
.right-btn {
  width: 69.12px;
  height: 69.12px;
  padding-top: 17.28px;
  padding-bottom: 17.28px;
}

.left-btn {
  width: 69.12px;
  height: 69.12px;
  padding-top: 17.28px;
  padding-bottom: 17.28px;
}

.calendar-container {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 10px;
  box-sizing: border-box; /* padding 포함한 크기 계산 */
}

.calendar-header {
  font-size: 40.32px;
  font-weight: 700;
  line-height: 67.39px;
  width: 700px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.calendar-days {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  font-weight: bold;
  text-align: center;
}

.day {
  color: #8892a0;
  font-size: 40.32px;
  font-weight: 500;
  line-height: 60.48px;
  word-wrap: break-word;
  padding: 48px;
}

.calendar-dates {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  grid-auto-rows: 1fr; /* 모든 행의 높이를 동일하게 설정 */
  grid-gap: 5px;
  flex-grow: 1; /* 남은 공간을 채우도록 설정 */
  text-align: center;
}

.date {
  cursor: pointer;
  width: 55px;
  height: 55px;
  padding: 35px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #3e444e;
  font-size: 46.08px;
  font-weight: 500;
  line-height: 74.88px;
  word-wrap: break-word;
}

.date:hover,
.date.selected {
  background: #5d8bff;
  color: whitesmoke;
  border-radius: 288px;
}

.date.highlight {
  background: #eff4ff;
  border-radius: 50%;
}

.date.empty {
  background-color: transparent;
}

.date.today {
  background: #5d8bff;
  border-radius: 288px;
  color: white;
}
</style>
