<template>
  <div>
    <h2>我是首页</h2>
    <p>我是首页内容，哈哈哈</p>

    <!--    注意：子组件的跳转需要要写完整的路径，不能只写'/news'这种子组件的路由-->
    <router-link to="/home/news">新闻</router-link>
    <router-link to="/home/message">消息</router-link>
    <!--    /home的子路由所对应的组件展示在下面的router-view里-->
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "Home"
}
</script>

<style scoped>

</style>
