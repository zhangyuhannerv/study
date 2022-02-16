import Vue from 'vue'
import App from './App.vue'
import router from "./router";
// import ElementUI from 'element-ui';
// import 'element-ui/lib/theme-chalk/index.css';
import store from "./store";
import toast from "components/common/toast";
// Vue.use(ElementUI);
// 安装toast插件
Vue.use(toast)

Vue.config.productionTip = false
// 将新的Vue实例作为事件总线
Vue.prototype.$bus = new Vue()

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
