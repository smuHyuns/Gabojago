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
    console.error('여행 추가(POST) 요청 실패:', error);
    throw error;
  }
};
