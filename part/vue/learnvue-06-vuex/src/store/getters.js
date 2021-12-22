export default {
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

}
