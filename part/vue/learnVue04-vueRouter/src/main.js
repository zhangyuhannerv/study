import Vue from 'vue'
// import router from './router/index'
import router from './router'// 如果这里最后是个目录，它会自动去找目录里的index.js文件。所有最后的index可以省略不写

import App from './App'
import DynamicRouteApp from './components/studyPart/DynamicRouteApp'


Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  render: h => h(App)// 路由的基本演示和使用,路由嵌套
  // render: h => h(DynamicRouteApp)// 动态路由
})
