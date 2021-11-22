// 注意导入的时候和之前讲导入语法的时候有点区别，不用写.js。webpack会根据名称直接找到对应的js文件

// 1.先用commonJS导入
const {add, mul} = require("./js/mathUtil")

console.log(add(20, 30))
console.log(mul(20, 30))

// 2.使用ES6导入
import {name, age, height} from "./js/info"

console.log(name)
console.log(age)
console.log(height)

// 综上，可以看到。在webpack下，使用任何的模块化规范（包括AMD，CMD，ES6，commonJS....）都可以，甚至可以同时使用。

// 3.依赖css文件（将css作为module)
require('./css/normal.css')
// 4.依赖less文件
// import('./css/special.less')// 这种es6导入css的方式不行。目前还没有找到es6导入css模块的正确写法
require('./css/special.less')

// 5.使用vue进行开发
// 这种不写路径的写法是直接从node-modules里找到对应的模块导出
import Vue from 'vue'

// 这个App还可以抽取。抽成一个单独的模块。来引入

// 第一步抽取(h5和js混合)
// import App from './vue/app'// js文件不需要写.js后缀

// 最终抽取(h5,js,css分离),此时打包是报错的。因为没有对应的loader解析.vue文件
// 解析.vue的loader:vue-loader vue-template-compiler
import App from './vue/App.vue'// vue文件必须要写.vue后缀

// const App = {
//   template: `
//     <div>
//     <h2>{{ message }}</h2>
//     <h2>{{ name }}</h2>
//     <button @click="btnClick">按钮</button>
//     </div>`,
//   data() {
//     return {
//       message: 'hello webpack',
//       name: '张三'
//     }
//   },
//   methods: {
//     btnClick() {
//       console.log("111");
//     }
//   },
// }

// 这个const app主要是为了前台调试用。在真正的开发中可以去掉
// const app = new Vue({
new Vue({
  // app内容的抽取，只需保留el和template即可
  el: '#app',
  // 因为app里面不能有任何内容，所以把要求有的内容写到template属性里面去
  // template里面的h5代码会完完整整的替换掉<div id='app'></div>。注意是替换，不是放到div#app里面去
  // template: `
  //   <div>
  //   <h2>{{ message }}</h2>
  //   <h2>{{ name }}</h2>
  //   <button @click="btnClick">按钮</button>
  //   </div>`,

  // 相当于直接把div#app替换成<App></App>。注意：这是有效的。因为它是template的属性。它是能够被编译出来的
  // 这里和以前在dom节点里面使用组件标签稍微有点区别。仔细体会
  template: `
    <App></App>`,

  // data: {
  //   message: 'hello webpack',
  //   name: '张三'
  // },
  // methods: {
  //   btnClick() {
  //     console.log("111");
  //   }
  // },
  components: {
    App,
  }

})
