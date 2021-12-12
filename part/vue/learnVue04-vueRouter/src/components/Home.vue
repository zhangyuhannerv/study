<template>
  <div>
    <h2>我是首页</h2>
    <p>我是首页内容，哈哈哈</p>

    <!--    注意：子组件的跳转需要要写完整的路径，不能只写'/news'这种子组件的路由-->
    <router-link to="/home/news">新闻</router-link>
    <router-link to="/home/message">消息</router-link>
    <!--    /home的子路由所对应的组件展示在下面的router-view里-->
    <router-view></router-view>

    <h2>{{ message }}</h2>
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      message: "home组件Message",
    };
  },
  created() {
    console.log("created"); // 组件创建
    // 这里可以改变当前网页的标题
    // document.title = "首页";
    // 但是这种改变标题的方式要在每个需要跳转的组件上都加个改变标题的代码
    // 因此这里需要全局导航守卫
    // 全局导航守卫就是专门监听路由跳转的过程
  },
  mounted() {
    console.log("mounted"); // 组件挂载
  },
  updated() {
    console.log("updated"); // 页面刷新
    // 什么时候页面会刷新呢？
    // 具体可以看官方文档
    // 这里举个例子。当this.message刷新的时候，页面上的{{message}}也会
    // 刷新，此时就会进入这个updated()回调函数
  },
};
</script>

<style scoped>
</style>
