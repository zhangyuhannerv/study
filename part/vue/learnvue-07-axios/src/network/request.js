import axios from "axios";

// 第一种封装方式，传入config，成功回调函数和失败回调函数
export function instance1(config, success, failure) {
  // 1.创建axios的实例
  const instance = axios.create({
    baseURL: "http://123.207.32.32:8000",
    timeout: 5000,
  })

  //  发送真正的网络请求
  instance(config).then(res => success(res)).catch(err => failure(err));
}

// 第二中封装方式,相当于把网络配置项和成功回调和失败回调都放在一个config对象里了
export function instance2(config) {
  // 1.创建axios的实例
  const instance = axios.create({
    baseURL: "http://123.207.32.32:8000",
    timeout: 5000,
  })

  //  发送真正的网络请求
  instance(config.baseConfig).then(res => config.success(res)).catch(err => config.failure(err));
}

// 第三种封装方式，终极封装方案
export function instance3(config) {
  // 1.创建axios的实例
  const instance = axios.create({
    baseURL: "http://123.207.32.32:8000",
    timeout: 5000,
  })
  // return new Promise((resolve, reject) => {
  //   //  发送真正的网络请求
  //   instance(config)
  //     .then(res => resolve(res))
  //     .catch(err => reject(err));
  // })

  // 2.axios的拦截器
  // axios.interceptors // 全局拦截
  // 自定义实例拦截
  // 请求拦截
  instance.interceptors.request.use(
    config => {
      // 请求成功，拦截下来的就是配置信息
      console.log(config)

      // 请求拦截的应用场景
      // 1.比如config中的一些信息不符合服务器的要求，对config进行一些特殊的定制
      // 2.每次发送请求时都希望在界面中显示一个请求的图标
      // 3.某些网络请求（比如登陆）是必须携带一些特殊的信息（Token）,如果token失效，跳转到登陆界面，重新登陆。

      // 必须把config对象返回，否则整个网络请求失败，会进入调用该实例的catch()方法中
      return config
    }, err => {
      // 请求失败，很少进入，一般网络断开可能会进入
      console.log(err)
    })

  // 响应拦截
  instance.interceptors.response.use(
    res => {
      // 响应成功，拦截的是服务器返回的结果
      console.log('响应拦截成功：')
      console.log(res);

      // 响应拦截的应用场景
      // 1.去除axios封装的其他属性
      // 2.对返回结果进行一些处理

      // 一定要返回一个结果。否则，调用该实例的then()方法中得不到响应的结果
      return res.data
    },
    err => {
      // 响应失败，打印错误信息
      console.log('响应拦截失败：')
      console.log(err)
    }
  )

  // 3.发送真正的网络请求
  return instance(config)
}
