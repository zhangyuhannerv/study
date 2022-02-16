/**
 * toast插件的封装方式
 */

const obj = {}

// 一旦Vue.use(obj),那么本质上就是执行obj.install()
//
obj.install = function () {
  console.log('执行了obj的install函数')
}

export default obj
