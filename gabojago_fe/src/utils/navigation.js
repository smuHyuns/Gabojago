// src/utils/navigation.js
import { useRouter } from 'vue-router';

export const useNavigation = () => {
  const router = useRouter();

  const navigateToProfile = () => {
    router.push('/profile');
  };

  const navigateToDashboard = () => {
    router.push('/dashboard');
  };

  return {
    navigateToProfile,
    navigateToDashboard,
  };
};
