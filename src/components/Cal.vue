<template>
  <div class="calendar-container">
    <h1 style="color: black">{{ selectedDateValue }}</h1>
    <div class="calendar-header">
      <img src="../assets/chevron-right.png" @click="prevMonth" class="right-btn" />
      <h2>{{ currentMonthYear }}</h2>
      <img src="../assets/chevron-left.png" @click="nextMonth" class="left-btn" />
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
            selected: selectedDates.includes(date.key),
            highlight: highlightDates.includes(date.key),
          },
        ]"
        @click="selectDate(date)"
      >
        {{ date.value }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue';

const today = new Date();
const currentMonth = ref(today.getMonth());
const currentYear = ref(today.getFullYear());

const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];

const currentMonthYear = computed(() => `${currentYear.value}년 ${currentMonth.value + 1}월`);

const calendarDates = ref([]);
const selectedDates = ref([]);
const highlightDates = ref([]);
const selectedDateValue = ref('');
const emit = defineEmits(['dateSelected']);

function handleDateSelect(date) {
  emit('dateSelected', date); // 선택한 날짜를 부모 컴포넌트로 전달
}

function renderCalendar() {
  const firstDayOfMonth = new Date(currentYear.value, currentMonth.value, 1);
  const daysInMonth = new Date(currentYear.value, currentMonth.value + 1, 0).getDate();
  const startDayOfWeek = firstDayOfMonth.getDay();

  const dates = [];

  for (let i = 0; i < startDayOfWeek; i++) {
    dates.push({ key: `empty-${i}`, value: '', empty: true });
  }

  for (let i = 1; i <= daysInMonth; i++) {
    dates.push({ key: `date-${i}`, value: i, empty: false });
  }

  calendarDates.value = dates;
}

function prevMonth() {
  currentMonth.value--;
  if (currentMonth.value < 0) {
    currentMonth.value = 11;
    currentYear.value--;
  }
  renderCalendar();
  updateHighlightDates();
}

function nextMonth() {
  currentMonth.value++;
  if (currentMonth.value > 11) {
    currentMonth.value = 0;
    currentYear.value++;
  }
  renderCalendar();
  updateHighlightDates();
}

function selectDate(date) {
  if (!date.empty) {
    const index = selectedDates.value.indexOf(date.key);
    if (index === -1) {
      if (selectedDates.value.length < 2) {
        selectedDates.value.push(date.key);
      } else {
        const dateValues = selectedDates.value.map((key) => {
          const day = calendarDates.value.find((d) => d.key === key).value;
          return day;
        });

        if (date.value < Math.min(...dateValues)) {
          const maxKey = selectedDates.value[dateValues.indexOf(Math.max(...dateValues))];
          selectedDates.value.splice(selectedDates.value.indexOf(maxKey), 1);
        } else {
          const minKey = selectedDates.value[dateValues.indexOf(Math.min(...dateValues))];
          selectedDates.value.splice(selectedDates.value.indexOf(minKey), 1);
        }
        selectedDates.value.push(date.key);
      }
      updateSelectedDateValue();
    } else {
      selectedDates.value.splice(index, 1);
      updateSelectedDateValue();
    }
    updateHighlightDates();
    emit(
      'dateSelected',
      selectedDates.value.map((key) => formatDateKey(key))
    );
  }
}

function formatDateKey(key) {
  const day = calendarDates.value.find((date) => date.key === key).value;
  return `${currentYear.value}-${(currentMonth.value + 1).toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`;
}

function updateSelectedDateValue() {
  selectedDateValue.value = selectedDates.value
    .sort((a, b) => {
      const dateA = calendarDates.value.find((date) => date.key === a).value;
      const dateB = calendarDates.value.find((date) => date.key === b).value;
      return dateA - dateB;
    })
    .map((key) => {
      const day = calendarDates.value.find((date) => date.key === key).value;
      return `${currentYear.value}년 ${currentMonth.value + 1}월 ${day}일`;
    })
    .join(', ');
}

function updateHighlightDates() {
  if (selectedDates.value.length === 2) {
    const [firstKey, secondKey] = selectedDates.value.sort((a, b) => {
      const dateA = calendarDates.value.find((date) => date.key === a).value;
      const dateB = calendarDates.value.find((date) => date.key === b).value;
      return dateA - dateB;
    });

    const firstDate = calendarDates.value.find((date) => date.key === firstKey).value;
    const secondDate = calendarDates.value.find((date) => date.key === secondKey).value;

    highlightDates.value = calendarDates.value.filter((date) => date.value > firstDate && date.value < secondDate).map((date) => date.key);
  } else {
    highlightDates.value = [];
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
  border-radius: 288px;
  color: white;
}

.date.highlight {
  background: #eff4ff;
  border-radius: 50%;
}

.date.empty {
  background-color: transparent;
}
</style>
