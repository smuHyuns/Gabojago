import './assets/main.css';
import { createApp } from 'vue';
import { createPinia } from 'pinia';
import App from './App.vue';
import router from './router';

const app = createApp(App);

app.use(createPinia());
app.use(router);

app.mount('#app');

// V-Calendar 설정
import { setupCalendar, Calendar, DatePicker } from 'v-calendar';
import 'v-calendar/style.css';

app.use(setupCalendar, {});
app.component('VCalendar', Calendar);
app.component('VDatePicker', DatePicker);
