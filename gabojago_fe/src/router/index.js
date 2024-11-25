import { createRouter, createWebHistory } from 'vue-router';
import Home from '../pages/Home.vue';
import Profile from '@/pages/Dashboard/Profile.vue';
import DashBoard from '@/pages/Dashboard/dashboard.vue';
import Gun from '@/pages/Gun.vue';
import Siwan from '@/pages/Siwan.vue';
import TravelName from '@/pages/TravelName.vue';
import AddPayment from '@/pages/AddPayment.vue';
import Calendar from '@/pages/Calendar.vue';
import Modal from '@/components/Modal.vue';
import Member from '@/pages/Member.vue';
import Err from '@/pages/Err.vue';
import Siwan_test from '@/pages/Siwan_test.vue';
import DeletePage from '@/pages/DeletePage.vue';
import Start from '@/pages/User/Start.vue';
import Trip_Detail from '@/pages/Trip/Trip_Detail.vue';
import AddPaymentFromDate from '@/pages/AddPaymentFromDate.vue';
import Siwan_test_full from '@/pages/Siwan_test_full.vue';
import Siwan_test_full_delete from '@/pages/Siwan_test_full_delete.vue';
import Login from '@/pages/User/Login.vue';
import Sign_up from '@/pages/User/Sign_up.vue';

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
    component: DashBoard,
  },
  {
    path: '/gun',
    name: 'gun',
    component: Gun,
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
    path: '/member',
    name: 'member',
    component: Member,
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
    path: '/TravelName',
    name: 'TravelName',
    component: TravelName,
  },
  {
    path: '/addpayment',
    name: 'AddPayment',
    component: AddPayment,
  },
  {
    path: '/calendar',
    name: 'Calendar',
    component: Calendar,
  },
  {
    path: '/',
    name: 'Start',
    component: Start,
  },
  {
    path: '/detail/:tripId',
    name: 'Trip_Detail',
    component: Trip_Detail,
  },
  // {
  //   path: '/accountCalendar/:tripId',
  //   name: 'Tokyo_calendar',
  //   component: Tokyo_calendar,
  //   props: true,
  // },
  {
    path: '/addPaymentFromDate',
    name: 'AddPaymentFromDate',
    component: AddPaymentFromDate,
  },
  {
    path: '/siwan_test_full/:tripId',
    name: 'siwan_test_full',
    component: Siwan_test_full,
    props: true,
  },
  {
    path: '/siwan_test_full_delete/:tripId',
    name: 'siwan_test_full_delete',
    component: Siwan_test_full_delete,
    props: true,
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;
