import Vue from 'vue'
import App from './App'
import cpn1 from './components/cpn1'

Vue.config.productionTip = false

/* eslint-disable no-new */

// 自定义个组件供createElement()函数掉用
const cpn = {
  template: `
  <div>你好啊</div>
 `,
  data() {
    return {
      message: '我是组件message'
    }
  }
}

console.log(App) // 没有template属性
console.log(cpn) // 有template属性
console.log(cpn1) // 没有template属性

new Vue({
  el: '#app',

  // runtime and compiler（创建runtime and compiler的项目，会是以下的代码。注意：此时的项目仍然可以
  // 注释掉下面两行代码并使用render()函数）
  // components: { App },
  // template: '<App/>',

  // runtime only(创建runtime only的项目，会是以下的代码)
  // render: h => h(App), // h的本名叫createElement

  // 关于createElement()函数的相关说明
  // createElement('标签','标签的属性',['标签内容1','标签内容2'])
  // 例子：return CreateElement('h2',{class:'box'},['hello']),那么就会创建一个h2标签,class的值为box，内容为hello
  // 将<h2 class='box'>hello</h2>替换掉#app

  // createElement的示例1：自己创建标签
  // render(createElement) {
  //   // return createElement('h2', { class: 'box' }, ['hello vue'])
  //   return createElement('h2', { class: 'box' },
  //     ['hello vue', createElement('button', ['按钮'])])
  // }

  // createElement的示例2：传入一个组件对象
  render(createElement) {
    // return createElement(cpn)// 这里会报错。因为该项目是run time only的。该组件对象有template属性，在此模式下无法解析。把它放到runtime and compiler里能看到效果
    // return createElement(App) // 但是这里能渲染App对象，是因为在解析.vue文件的时候已经将template去掉了。

    // 即要想让上面的也生效，那么需要将cpn分离到.vue文件里即可，不能直接写到js文件里
    return createElement(cpn1); // 可以看到刚才的报错不见了

    // 那么.vue文件是由谁处理的呢？
    // 是由vue-template-compiler,他是npm的一个包。在webpack里配置后使用。之前讲过
  }

})
// runtime only mode
// render->vDom->ui
// 比runtime and compiler少了个template->ast->render的过程  

// 结论
// 1.runtime only的性能更高
// 2.runtime only的代码量更少
// 综上，以后开发vue项目，都要选择runtime only的模式