import './assets/main.css';

import { createApp } from 'vue';
import { createPinia } from 'pinia';

import App from './App.vue';
import router from './router';

const app = createApp(App);

app.use(createPinia());
app.use(router);

app.mount('#app');

import { setupCalendar, Calendar, DatePicker } from 'v-calendar';
import 'v-calendar/style.css';

// Use plugin defaults (optional)
app.use(setupCalendar, {});

// Use the components
app.component('VCalendar', Calendar);
app.component('VDatePicker', DatePicker);
