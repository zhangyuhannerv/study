import Vue from "vue";
import Vuex from "vuex"
import mutations from "./mutations";
import actions from "./actions";
import getters from "./getters";
import moduleA from "./modules/moduleA";
// 1.安装插件
Vue.use(Vuex); // 底层执行Vuex.install()

// 2.创建对象
// 关于vuex单一状态树(单一数据源)的理解：
// 不要建多个store，只建立一个store
// 所有的状态都在下面的store里，方便维护和管理
const state = {
  shareCounter: 1000,
  students: [
    {id: 110, name: 'why', age: 18},
    {id: 111, name: 'kobe', age: 24},
    {id: 112, name: 'james', age: 30},
    {id: 113, name: 'curry', age: 10},
  ],
  info: {
    name: 'kobe',
    age: 40,
    height: 1.98
  }
};

const store = new Vuex.Store({
  state,
  mutations,
  // 如果需要在vuex里做一些异步操作，那么就需要使用action
  // action类似mutation,它是用来代替mutation执行异步操作的
  actions,
  // getters可以近似的理解为组件里的计算属性
  getters,
  modules: {
    // vuex是单一状态树，那么也就意味着很多状态都会交给vuex管理
    // 当应用变得十分复杂时，store对象就可能变得相当臃肿
    // 为了解决这个问题，vuex允许将store分割成模块（modules），而每个模块都拥有自己的state,mutations,actions,getters。
    // 甚至module下面还有子模块module。但是很少这么分，一般modules下面只需一级即可，不建议再分二级，三级模块
    a: moduleA
  }
})

// 3.导出对象
export default store
