import { useAuthStore } from '@/stores/auth';
import axios from 'axios';
import { API_BASE_URL } from '@/config/config';

const BASEURL = `${API_BASE_URL}/user`;

export const updateNewProfile = async (payload) => {
  try {
    const authStore = useAuthStore();
    await axios.post(`${BASEURL}/profile`, payload, {
      headers: { Authorization: `Bearer ${authStore.token}` },
    });
  } catch (error) {
    // console.log('updateProfile / 프로필 업데이트 실패 : ', error);
    throw error;
  }
};

export const getUserProfile = async () => {
  try {
    const authStore = useAuthStore();
    const response = await axios.get(`${BASEURL}/profile`, {
      headers: { Authorization: `Bearer ${authStore.token}` },
    });
    return response;
  } catch (error) {
    // console.log('getUserProfile / 유저프로필가져오기 업데이트 실패 : ', error);
    throw error;
  }
};

export const signUp = async (
  userNickname,
  userPassword,
  userLoginId,
  userUsername,
  userEmail,
  userGender,
  userBirth
) => {
  try {
    const request = {
      user_nickname: userNickname,
      user_password: userPassword,
      user_login_id: userLoginId,
      user_username: userUsername,
      user_email: userEmail,
      user_gender: userGender,
      user_birth: userBirth,
    };
    const response = await axios.post(`${BASEURL}/sign-up`, request);
    return response;
  } catch (error) {
    // console.log('signUp / 회원가입 실패 : ', error);
    throw error;
  }
};

export const findId = async (username, userEmail) => {
  try {
    const response = await axios.get(`${BASEURL}/find-id`, {
      params: { email: userEmail, username: username },
    });
    return response.data.userLoginId;
  } catch (error) {
    console.log('findId / 아이디찾기 실패 : ', error);
    throw error;
  }
};

export const authEmail = async (userEmail) => {
  try {
    const request = {
      email: userEmail,
    };
    console.log('요청 : ', request);
    const response = await axios.post(`${BASEURL}/authEmail`, request);
    return response;
  } catch (error) {
    console.log('authEmail / 인증번호받아오기 실패 : ', error);
    throw error;
  }
};
