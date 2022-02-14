import Vue from "vue";
import Vuex from 'vuex'
import mutations from "./mutations";
import actions from "./actions";
import getters from "./getters";

// 1.安装插件
Vue.use(Vuex)

// 2.创建store对象
const state = {
  cartList: []// 购物车数组
}

const store = new Vuex.Store({
  state,
  // mutations唯一的目的就是为了修改state中的状态
  // mutation中的每个方法完成事件尽可能比较单一一点
  mutations,
  actions,
  getters
})

// 3.挂载到vue实例上
export default store
