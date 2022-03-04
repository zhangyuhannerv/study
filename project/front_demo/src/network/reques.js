import axios from "axios";

const baseURL = "http://127.0.0.1/backDemo"
const timeout = 5000

export function request(config) {
  // 1.创建axios的实例
  const instance = axios.create({
    baseURL,
    timeout,
  })

// 添加请求拦截器
  instance.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    return config;
  }, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  });

// 添加响应拦截器
  instance.interceptors.response.use(function (response) {
    // 对响应数据做点什么
    return response.data;
  }, function (error) {
    // 对响应错误做点什么
    return Promise.reject(error);
  });

  // 3.发送真正的网络请求
  return instance(config)
}
