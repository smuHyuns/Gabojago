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
      params: { tripId: tripId.value },
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    console.log('currency 불러오기 요청 실패 : ', error);
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
    console.error('거래 추가(POST) 요청 실패:', error);
    throw error;
  }
};
