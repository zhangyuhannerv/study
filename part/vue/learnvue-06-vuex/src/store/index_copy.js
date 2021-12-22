import Vue from "vue";
import Vuex from "vuex"
import * as types from './mutation-types'

// 1.安装插件
Vue.use(Vuex); // 底层执行Vuex.install()

// 创建vuex的不同模块
const moduleA = {
  state: {
    name: 'zhangsan'
  },
  mutations: {
    // 注意：模块里的mutation的名字不要和store里的mutation的名字重复
    updateAName(state, payload) {
      console.log(payload)
      state.name = 'lisi'
    }
  },
  getters: {
    fullName(state) {
      return state.name + '111'
    },
    fullName2(state, getters) {
      return getters.fullName + '222'
    },
    fullName3(state, getters, rootState) {
      // 访问store里的getters
      return getters.fullName2 + rootState.shareCounter
    }
  },
  actions: {
    aUpdateName(context) {
      console.log(context);// 打印发现里面还有getters，rootGetters,state,rootState,见名知意，如果想取别的值，直接取即可
      // 注意：此时的context，它就不再是store对象了，它具体是什么老师没说
      // 但是此时的context也有个commit，它只能commit他所在模块里的mutation，不能commit外部store的mutation
      setTimeout(() => {
        context.commit('updateAName', 'wangwu')
      }, 1000)
    }
  },
}

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
    ],
    info: {
      name: 'kobe',
      age: 40,
      height: 1.98
    }
  },
  mutations: {
    // 方法
    // 默认把state传进去
    increment(state) {
      state.shareCounter++
    },
    decrement(state) {
      state.shareCounter--
    },
    incrementCount(state, count) {
      // 1.普通的提交风格这里的count就是commit里的第二个参数(5,10.....)
      // console.log(count);
      // state.shareCounter += count

      // 2.对象的提交风格。这里的count是形如下面的对象（就是commit多提交的对象)
      // {
      //   type:'incrementCount',
      //   count:10,
      //   age:18,
      // }
      // 这种情况下，建议把上面的第二个参数count直接改名为payload(载荷)更合适
      console.log(count);
      state.shareCounter += count.count
    },
    addStudent(state, stu) {
      state.students.push(stu)
    },

    updateInfo(state) {
      state.info.name = 'zyh'
    },

    updateInfo1(state) {
      state.info.address = '洛杉矶'
    },
    // updateInfo2(state) {
    [types.UPDATEINFO2](state) { // 这里是用mutation-types里定义的类型常量来作为方法的名称，注意这种新的定义方法的方式['test'](){}
      // 正确的新增属性的方式
      Vue.set(state.info, 'address', '洛杉矶')

      // 错误的删除属性的方式(js原生删除对象属性的方式，此时没有经过vue的响应式系统，界面不会发生变化)
      // delete state.info.age

      // 正确的删除对象属性的方式，此时经过了vue的响应式系统，界面会发生变化
      Vue.delete(state.info, 'age')

      // 注意：这种对于对象/数组初始化的属性能做到响应式，但是对于对象新增的属性或者对象删除的属性，必须经过特殊的方式才能做到响应式
      // 的现象，不仅在vuex里存在，在组件里是同样存在的
    },
    [types.UPDATEINFO3](state) {
      // 此时发现虽然info的age成功改变了，但是devtools跟踪不到了，最新的信息显示age还是40
      // 因此，mutation里的方法或者说代码必须是同步的，不能像下面那样是异步的
      setTimeout(() => {
        state.info.age = 80
      }, 1000)
    },
    [types.UPDATENAME](state) {
      state.info.name = '异步改名字了'
    }
  },
  // 如果需要在vuex里做一些异步操作，那么就需要使用action
  // action类似mutation,它是用来代替mutation执行异步操作的
  actions: {
    // context:上下文，在这里要理解为store对象
    updateName(context, payload) {
      console.log(payload)
      // 异步的操作
      setTimeout(() => {
        // 异步的回调函数，通过commit修改state
        context.commit(types.UPDATENAME)
      }, 1000)
    },

    // 异步修改信息之后，能成功回调，告诉外面，改的成功与否，改的结果是啥
    updateName1(context, payload) {
      // action返回一个promise对象，在外部dispatch当前action时的时候，写then或者catch
      return new Promise((resolve, reject) => {
        // 异步的操作
        setTimeout(() => {
          // 异步的回调函数，通过commit修改state
          context.commit(types.UPDATENAME)
          console.log(payload)
          // 成功了
          resolve('成功了')
          // 失败了
          // reject('失败了')
        }, 1000)
      })

    }
  },
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
