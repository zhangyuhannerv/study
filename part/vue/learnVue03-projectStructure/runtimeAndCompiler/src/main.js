// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  components: { App },
  template: '<App/>'
})

// 测试esLint,必须严格按照下面的代码写一个方法。忒麻烦了。
// function sum (num1, num2) {
//   return num1 + num2
// }

// console.log(sum(10, 20))

// 经测试，再也不想用esLint了
// 这是，就需要手动关掉esLint
// 在config的index.js里把useEslint从true改为false即可
// 再使用下面的代码发现可以了
function sum(num1, num2) {
  return num1 + num2
}

console.log(sum(10, 20))