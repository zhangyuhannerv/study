/**
 * toast插件的封装方式
 */

import Toast from './Toast'

const obj = {}

// 一旦Vue.use(obj),那么本质上就是执行obj.install()
obj.install = function (Vue) {
  // 给body拼接上toast元素(失败,Toast.$el是undefined)
  // console.log(Toast.$el)
  // document.body.appendChild(Toast.$el)

  // 正确的方式：
  // 1.创建组件构造器
  const toastConstructor = Vue.extend(Toast)
  // 2.通过new的方式，根据组件构造器，可以创建出来一个组件对象
  const toast = new toastConstructor()
  // 3.将组件对象，手动的挂载到某一个元素上(这里是挂载自己创建出来的div上)
  toast.$mount(document.createElement('div'))
  // 4.此时toast.$el对应的就是上面创建出来的div了
  document.body.appendChild(toast.$el)


  // 给Vue原型加上$toast属性方便所有组件调用
  Vue.prototype.$toast = toast
}

export default obj
