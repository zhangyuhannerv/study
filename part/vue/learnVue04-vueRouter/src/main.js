import Vue from 'vue'
// import router from './router/index'
import router from './router' // 如果这里最后是个目录，它会自动去找目录里的index.js文件。所有最后的index可以省略不写

import App from './App'
import DynamicRouteApp from './components/studyPart/DynamicRouteApp'
import QueryTransParam from './components/studyPart/QueryTransParam.vue'


Vue.config.productionTip = false

// prototype：原型。
// 如果在Vue的原型上加个test(),那么所有的组件都会有test()
Vue.prototype.test = function() {
  console.log('test');
}

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  render: h => h(App) // 路由的基本演示和使用,路由嵌套
  // render: h => h(DynamicRouteApp)// 动态路由
  // render: h => h(QueryTransParam) // 演示使用Query的方式传递参数
})

console.log(router);