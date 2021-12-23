// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import axios from 'axios'

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  components: {App},
  template: '<App/>'
})

// 1.演示axios最最简单的使用
// 123.207.32.32(学习时老师搭建的网站)
axios({
  // 没有定义method,默认就发送get请求
  url: 'http://123.207.32.32:8000/home/multidata',
  method: 'get'// method这里根据不同的接口也可以选择'post'
}).then(res => {
  console.log(res)
})

axios.get('http://123.207.32.32:8000/home/multidata').then(res => console.log(res));

axios.get('http://123.207.32.32:8000/home/data', {
  // 注意，get方式这里写param。post方式写data。具体的可以看官方文档
  params: {
    type: 'pop',
    page: 1,
  }
}).then(res => console.log(res))


// 尝试访问城轨：失败了
// axios({
//   // 没有定义method,默认就发送get请求
//   url: 'http://127.0.0.1:8088/TunnelNoise/getIsJxFile',
//   method: 'get',
//   data: {
//     xmId: 'f26efb3db10b44ddb503b3877ac04c07',
//     csnrId: '17d9122351c748b6a1c84e3d4075b94b',
//   }
// }).then(res => {
//   console.log(res)
// })


// 2.axios同时发送并发请求
axios.all([axios({
  url: 'http://123.207.32.32:8000/home/multidata',
}), axios({
  url: 'http://123.207.32.32:8000/home/data',
  params: {
    type: 'sell',
    page: 4,
  }
})]).then(res => {

// })]).then(([res1, res2]) => {// 这是es6的数组解构对返回结果直接分割开，下面还有axios自带的spread()方法也能分割，推荐使用下面的
  // 此时打印的是个数组
  console.log(res)

  // console.log('第一个请求：')
  // console.log(res1)
  // console.log('第二个请求：')
  // console.log(res2)
})

// 并发请求时利用axios的spread()将请求按照顺序进行分割
axios.all([axios({
  url: 'http://123.207.32.32:8000/home/multidata',
}), axios({
  url: 'http://123.207.32.32:8000/home/data',
  params: {
    type: 'sell',
    page: 4,
  }
})]).then(axios.spread((res1, res2) => {
  console.log('第一个请求：')
  console.log(res1)
  console.log('第二个请求：')
  console.log(res2)
}))

// 3.设置全局axios默认值(可以把公共的属性抽取出来，在全局里设置)
axios.defaults.baseURL = 'http://123.207.32.32:8000'
axios.defaults.timeout = 5000 // 接口超时时间：5s
axios.get('/home/multidata').then(res => console.log(res));
