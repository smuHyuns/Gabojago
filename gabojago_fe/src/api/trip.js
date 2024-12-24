import { useAuthStore } from '@/stores/auth';
import axios from 'axios';
import { API_BASE_URL } from '@/config/config';

const BASEURL = `${API_BASE_URL}/trip`;

export const saveTrip = async (request) => {
  try {
    const formattedRequest = {
      country: request.country,
      headcount: request.headcount,
      start_period: request.start_period,
      end_period: request.end_period,
      description: request.description,
    };
    console.log(formattedRequest);

    const authStore = useAuthStore();
    const token = authStore.token;

    const response = await axios.post(`${BASEURL}/save`, formattedRequest, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    console.error('saveTrip / 여행 추가(POST) 요청 실패:', error);
    throw error;
  }
};

//DashBaord
export const getAllTrips = async () => {
  try {
    const authStore = useAuthStore();

    const response = await axios.get(`${BASEURL}/all`, {
      headers: { Authorization: `Bearer ${authStore.token}` },
    });

    return response;
  } catch (error) {
    console.error('getAllTrips / 여행목록 불러오기 실패 : ', error);
  }
};

export const getTripStatus = async (status) => {
  try {
    const authStore = useAuthStore();

    const response = await axios.get(`${BASEURL}/status`, {
      params: { status },
      headers: { Authorization: `Bearer ${authStore.token}` },
    });
    return response;
  } catch (error) {
    console.error('getTripStatus / 여행상태 불러오기 실패 :', error);
  }
};

export const getCurrency = async (tripId) => {
  try {
    const authStore = useAuthStore();
    const token = authStore.token;

    const response = await axios.get(`${BASEURL}/get-country`, {
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

export const getTripDetail = async (tripId) => {
  try {
    const authStore = useAuthStore();
    const token = authStore.token;

    const response = await axios.get(`${BASEURL}/detail/${tripId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response;
  } catch (error) {
    console.log('getTripDetail / 여행 세부내역 불러오기 요청 실패 : ', error);
    throw error;
  }
};
