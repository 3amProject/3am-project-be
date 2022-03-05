import axios from 'axios';
import setUpInterceptorsTo from './interceptors';

const customAxios = setUpInterceptorsTo(
  axios.create({
    baseURL: process.env.REACT_APP_API_URL,
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json',
    },
    withCredentials: true,
  })
);

export default customAxios;
