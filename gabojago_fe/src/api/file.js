import { useAuthStore } from '@/stores/auth';
import axios from 'axios';
import { API_BASE_URL } from '@/config/config';

const BASEURL = `${API_BASE_URL}/file`;

export const upload = async (formData) => {
  const authStore = useAuthStore();
  try {
    const response = await axios.post(`${BASEURL}/upload`, formData, {
      headers: {
        Authorization: `Bearer ${authStore.token}`,
      },
    });
    return response;
  } catch (error) {
    console.log('upload / 업로드 실패 : ', error);
    throw error;
  }
};
