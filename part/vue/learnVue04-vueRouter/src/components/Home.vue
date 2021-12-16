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
      path: '/home/news'
    };
  },
  created() {
    // this.$router.push('/home/news')
    console.log("home组件被创建"); // 组件创建
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
  destroyed() {
    console.log('home组件被销毁')
  },
  // 当前组件活跃的时候执行,虽然有报错，但是能解决问题。。。
  activated() {
    // console.log('activated');
    this.$router.push(this.path);
  },
  // // 当前组件不活跃的时候执行,结果，失败，因为此时获得path已经是新组件的path了
  // deactivated() {
  //   console.log('deactivated');
  //   this.path = this.route.path;
  // }
  // 既然deactivated()不合适，调整一下，改用组件内的的导航守卫
  beforeRouteLeave(to, from, next) {
    this.path = this.$route.path;
    next()
  }

  // 注意：activated()和deactivated()只有组件被<keep-alive>包裹的时候才能够执行(才有效)。
  // 首页中使用了个path记录了离开时的路径，再此进入首页时，重定向到离开时的路径。后面的开发中不用这种方式实现记录效果。而是使用currentIndex来记录
};
</script>

<style scoped>
</style>
