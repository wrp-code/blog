// http.js
import axios from 'axios';

// 创建axios实例
const service = axios.create({
  baseURL: import.meta.env.VITE_APP_BASE_API, // 根据环境变量设置基础URL
  timeout: 1000 // 请求超时时间
});

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 在发送请求之前可以做些什么
    // 例如：添加token
    const token = localStorage.getItem('token');
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  error => {
    // 对请求错误做些什么
    console.log(error); // for debug
    return Promise.reject(error);
  }
);

// 响应拦截器
service.interceptors.response.use(
  response => {
    // 对响应数据做点什么
    const res = response.data;
    
    // 假设后端返回的数据结构为 { code, message, data }
    if (res.code !== 10000) {
      // 处理业务错误
      console.error(res.message || 'Error');
      return Promise.reject(new Error(res.message || 'Error'));
    } else {
      return res.data;
    }
  },
  error => {
    // 对响应错误做点什么
    console.log('err' + error); // for debug
    
    // 处理HTTP错误
    if (error.response) {
      switch (error.response.status) {
        case 401:
          // 处理未授权
          break;
        case 403:
          // 处理禁止访问
          break;
        case 404:
          // 处理未找到
          break;
        case 500:
          // 处理服务器错误
          break;
      }
    }
    
    return Promise.reject(error);
  }
);

// 封装GET请求
export function get(url, params = {}) {
  return service({
    url,
    method: 'get',
    params
  });
}

// 封装POST请求
export function post(url, data = {}) {
  return service({
    url,
    method: 'post',
    data
  });
}

// 封装PUT请求
export function put(url, data = {}) {
  return service({
    url,
    method: 'put',
    data
  });
}

// 封装DELETE请求
export function del(url, params = {}) {
  return service({
    url,
    method: 'delete',
    params
  });
}

export function uploadFile(url, file, onUploadProgress = null) {
  const formData = new FormData();
  formData.append('file', file);
  
  return service({
    url,
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    onUploadProgress
  });
}

export default service;