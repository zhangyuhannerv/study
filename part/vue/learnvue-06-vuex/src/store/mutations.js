import * as types from "./mutation-types";
import Vue from "vue";

export default {
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
}
