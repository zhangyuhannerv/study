<template>
  <div id="app">
    <!--router-link是vueRouter注册的两个全局组件,它可以在项目的任意位置使用-->
    <!--    router-link最终是被默认渲染成a标签的-->

    <!--tag属性能修改router-link被渲染的标签,比如tag='button',那么router-link最后被渲染为button标签-->
    <!--replace能够给把默认的pushState()改为replaceState(),即不是向栈里放入新的url，而是替换栈顶的url。此时浏览器的返回和前进按钮都不可用-->
    <router-link to="/home" tag="button" replace>首页</router-link>
    <!--当router-link对应的路由匹配成功时，会自动给当前元素设置一个router-link-active的class,设置active-class可以修改默认的名称,通常使用默认的。在进行高亮显示的导航菜单，或者底部tabbar时，会用到该类-->
    <!--之所以不推荐更改active-class属性，是因为有很多个的时候，每个标签都要修改active-class属性。这种情况下有一种简便的写法，在路由的index.js里配置-->
    <router-link to="/about" replace active-class="active">关于</router-link>
    <!--因为已经全局修改了active-class，所以下面两个的活跃class也是active-->
    <router-link to="/about" replace>关于1</router-link>
    <router-link to="/about" replace>关于2</router-link>


    <!--定义按钮，通过代码实现路由的跳转-->
    <br>
    <div @click="homeClick">自定义按钮跳转到首页</div>
    <button @click="aboutClick">自定义按钮跳转到关于</button>

    <!--    router-view是组件显示的位置，会根据当前的路径，动态渲染出不同的组件-->
    <!--    网页的其他内容，比如顶部的标题/导航，或者底部的一些版权信息等会和router-view处于同一个等级-->
    <!--    路由切换时，切换的是router-view挂载的组件，其他内容不会发生改变-->
    <h2>导航栏</h2>
    <!--    这个router是用来展示最外层的东西。所以不能让子路由对应的组件在这个router-view里展示-->
    <router-view></router-view>
    <h2>版权信息</h2>
  </div>
</template>

<script>
export default {
  name: 'App',

  methods: {
    homeClick() {
      // 通过代码的方式修改url
      // this获得的时当前组件对象,$router是vue-router的源码里面向所有的组件都加了个$router属性
      // push()相当于pushState()方法，同样的它也有replace()方法，相当于replaceState()方法
      // this.$router.push('/home')
      this.$router.replace('/home')
    },
    aboutClick() {
      // this.$router.push('/about')
      this.$router.replace('/about')
    }
  }
}
</script>

<style>
.router-link-active {
  color: #f00;
}

.active {
  color: skyblue;
}
</style>
