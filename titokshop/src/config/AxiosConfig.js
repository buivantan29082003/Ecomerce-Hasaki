// axiosConfig.js
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 10000, 
  headers: {
    'Content-Type': 'application/json',
  },
});

// Thêm interceptors để tự động gắn token nếu có
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token'); // hoặc lấy từ Redux, Zustand,...
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Xử lý response lỗi chung
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response) {
      console.error('Lỗi API:', error.response.data);
    } else {
      console.error('Lỗi mạng:', error.message);
    }
    return Promise.reject(error);
  }
);

export default api;


