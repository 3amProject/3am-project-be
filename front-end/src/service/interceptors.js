import axios from 'axios';

const API_URL = 'http://localhost:8000';

const onRequest = (config) => {
  const accessToken = localStorage.getItem('accessToken');
  if (accessToken) {
    config.headers.Authorization = `Bearer ${accessToken}`;
  }

  return config;
};

const onRequestError = (error) => {
  return Promise.reject(error);
};

const onResponse = (response) => {
  return response;
};

const onResponseError = async (error) => {
  if (error) {
    if (error.response.status === 401) {
      const oldRefreshToken = localStorage.getItem('refreshToken');
      const userId = localStorage.getItem('userId');
      try {
        const res = await axios.post(`${API_URL}/auth/refreshToken`, {
          refreshToken: oldRefreshToken,
          userId,
        });
        const { accessToken, refreshToken } = res.data.data;
        localStorage.setItem('accessToken', accessToken);
        localStorage.setItem('refreshToken', refreshToken);
        return;
      } catch (_error) {
        console.log(Promise.reject(_error));
      }
    }
  }
};

const setUpInterceptorsTo = (axiosInstance) => {
  axiosInstance.interceptors.request.use(onRequest, onRequestError);
  axiosInstance.interceptors.response.use(onResponse, onResponseError);
  return axiosInstance;
};

export default setUpInterceptorsTo;
