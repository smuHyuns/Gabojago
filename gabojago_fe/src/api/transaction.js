import { useAuthStore } from '@/stores/auth';
import axios from 'axios';
import { API_BASE_URL } from '@/config/config';

const BASEURL = `${API_BASE_URL}/transaction`;

// 통화단위 불러오기 - with tripId

export const getCurrency = async (tripId) => {
  try {
    const authStore = useAuthStore();
    const token = authStore.token;

    const response = await axios.get(`${API_BASE_URL}/trip/get-country`, {
      params: { tripId: tripId },
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    console.log('getCurrency / 환율 불러오기 요청 실패 : ', error);
    throw error;
  }
};

export const postTransaction = async (request) => {
  try {
    const authStore = useAuthStore();
    const token = authStore.token;
    const response = await axios.post(`${BASEURL}/save-from-trip`, request, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    console.error('postTransaction / 거래 추가(POST) 요청 실패:', error);
    throw error;
  }
};

export const getDetailDayTransaction = async (tripId, selectedDate) => {
  try {
    const authStore = useAuthStore();
    const response = await axios.get(`${BASEURL}/detail-day-transaction`, {
      headers: { Authorization: `Bearer ${authStore.token}` },
      params: {
        tripId: tripId,
        tripDate: selectedDate,
      },
    });
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error(
      'getDetailDayTransaction / 하루 거래내역 불러오기 실패:',
      error.response?.data || error.message
    );
  }
};

export const deleteTransaction = async (tripId, selectedExpenses) => {
  try {
    const authStore = useAuthStore();
    await axios.delete(`${BASEURL}/delete`, {
      headers: { Authorization: `Bearer ${authStore.token}` },
      data: {
        tripId: tripId,
        transactionIds: selectedExpenses.value,
      },
    });
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error(
      'delete Transaction / 거래 삭제 실패:',
      error.response?.data || error.message
    );
  }
};
