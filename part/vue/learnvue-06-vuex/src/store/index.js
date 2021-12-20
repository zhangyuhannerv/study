import Vue from "vue";
import Vuex from "vuex"

// 1.安装插件
Vue.use(Vuex);// 底层执行Vuex.install()

// 2.创建对象
// 关于vuex单一状态树(单一数据源)的理解：
// 不要建多个store，只建立一个store
// 所有的状态都在下面的store里，方便维护和管理
const store = new Vuex.Store({
  state: {
    shareCounter: 1000,
    students: [
      {id: 110, name: 'why', age: 18},
      {id: 111, name: 'kobe', age: 24},
      {id: 112, name: 'james', age: 30},
      {id: 113, name: 'curry', age: 10},
    ]
  },
  mutations: {
    //  方法
    //  默认把state传进去
    increment(state) {
      state.shareCounter++
    },
    decrement(state) {
      state.shareCounter--
    }
  },
  actions: {},
  // getters可以近似的理解为组件里的计算属性
  getters: {
    // 定义一个getters,获取shareCounter的平方
    powSharedCounter(state) {
      return Math.pow(state.shareCounter, 2)
    },
    // 获取年龄大于20岁的学生
    more20stu(state) {
      return state.students.filter(e => e.age > 20)
    },

    // getters的属性本身可以用getters来做变量
    // 获取年龄大于20岁的学生的个数
    more20stuLength(state, getters) {
      // 这么写也可以，但是会多一步过滤的重复计算
      // return state.students.filter(e => e.age > 20).length

      //  正确写法
      return getters.more20stu.length
    },

    // getters的传值操作
    // 获取年龄大于age的学生，age是不确定的，是用getters的时候传过来的
    moreAgeStu(state) {
      // return function (age) {
      //   return state.students.filter(e => e.age > age)
      // }

      // 上面的简写
      return age => state.students.filter(e => e.age > age)
    }

  },
  modules: {}
})

// 3.导出对象
export default store
