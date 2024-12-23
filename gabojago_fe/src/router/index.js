import { createRouter, createWebHistory } from 'vue-router';
import Home from '../pages/Home.vue';
import Profile from '@/pages/Dashboard/Profile.vue';
import Siwan from '@/pages/Siwan.vue';
import Trip_AddDescription from '@/pages/Trip/Trip_AddDescription.vue';
import Modal from '@/components/compo/Modal.vue';
import Err from '@/pages/Err.vue';
import Siwan_test from '@/pages/Siwan_test.vue';
import DeletePage from '@/pages/DeletePage.vue';
import Start from '@/pages/User/Start.vue';
import Trip_Detail from '@/pages/Trip/Trip_Detail.vue';
import AddPaymentFromDate from '@/pages/AddPaymentFromDate.vue';
import DetailTransactionPage from '@/pages/Trip/Detail_transaction.vue';
import DetailTransaction_delete_Page from '@/pages/Trip/Detail_transaction_delete.vue';
import Login from '@/pages/User/Login.vue';
import Sign_up from '@/pages/User/Sign_up.vue';
import Dashboard from '@/pages/Dashboard/Dashboard.vue';
import Detail_AddPayment from '@/pages/Trip/Detail_AddPayment.vue';
import Trip_AddCountry from '@/pages/Trip/Trip_AddCountry.vue';
import Trip_AddCalendar from '@/pages/Trip/Trip_AddCalendar.vue';
import Trip_AddMember from '@/pages/Trip/Trip_AddMember.vue';
import Trip_AddPayment from '@/pages/Trip/Trip_AddPayment.vue';

const routes = [
  {
    path: '/sign-up',
    name: 'sign_up',
    component: Sign_up,
  },
  {
    path: '/login',
    name: 'login',
    component: Login,
  },
  {
    path: '/profile',
    name: 'profile',
    component: Profile,
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    component: Dashboard,
  },
  {
    path: '/siwan',
    name: 'siwan',
    component: Siwan,
  },
  {
    path: '/siwan_test/:date/:tripId',
    name: 'siwan_test',
    component: Siwan_test,
    props: true,
  },
  {
    path: '/deletepage/:date/:tripId',
    name: 'deletepage',
    component: DeletePage,
    props: true,
  },
  {
    path: '/err',
    name: 'err',
    component: Err,
  },
  {
    path: '/modal',
    name: 'modal',
    component: Modal,
  },

  {
    path: '/',
    name: 'Start',
    component: Start,
  },
  {
    path: '/add-trip',
    name: 'Trip_AddCountry',
    component: Trip_AddCountry,
  },
  {
    path: '/add-calendar',
    name: 'Trip_AddCalendar',
    component: Trip_AddCalendar,
  },
  {
    path: '/add-Member',
    name: 'Trip_AddMember',
    component: Trip_AddMember,
  },
  {
    path: '/add-description',
    name: 'Trip_AddDescription',
    component: Trip_AddDescription,
  },
  {
    path: '/add-payment',
    name: 'Trip_AddPayment',
    component: Trip_AddPayment,
  },
  {
    path: '/detail/:tripId',
    name: 'Trip_Detail',
    component: Trip_Detail,
  },
  {
    path: '/addPaymentFromDate',
    name: 'AddPaymentFromDate',
    component: AddPaymentFromDate,
  },
  {
    path: '/detail-transaction/:tripId',
    name: 'Detail_transaction',
    component: DetailTransactionPage,
    props: (route) => ({
      tripId: Number(route.params.tripId), // 여행 ID
      selectedDate: route.query.date, // 선택된 날짜
    }),
  },
  {
    path: '/detail-transaction/:tripId',
    name: 'Detail_transaction_delete',
    component: DetailTransaction_delete_Page,
    props: (route) => ({
      tripId: Number(route.params.tripId), // 여행 ID
      selectedDate: route.query.date, // 선택된 날짜
    }),
  },
  {
    path: '/detail/:tripId/addpayment',
    name: 'Detail_AddPayment',
    component: Detail_AddPayment,
    props: (route) => ({
      tripId: Number(route.params.tripId),
      selectedDate: route.query.date,
    }),
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;
