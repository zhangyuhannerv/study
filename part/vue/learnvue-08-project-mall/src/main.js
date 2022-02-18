import Vue from 'vue'
import App from './App.vue'
import router from "./router";
// import ElementUI from 'element-ui';
// import 'element-ui/lib/theme-chalk/index.css';
import store from "./store";
import toast from "components/common/toast";
import FastClick from 'fastclick'
import VueLazyload from "vue-lazyload";
// Vue.use(ElementUI);
// 安装toast插件
Vue.use(toast)

Vue.config.productionTip = false
// 将新的Vue实例作为事件总线
Vue.prototype.$bus = new Vue()
// 通过fastclick解决移动端点击的300ms延迟
FastClick.attach(document.body)
// 使用图片懒加载插件
Vue.use(VueLazyload, {
  loading: require('./assets/img/common/placeholder.png')
})

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
