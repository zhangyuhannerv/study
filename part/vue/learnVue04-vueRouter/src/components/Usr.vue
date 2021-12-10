<template>
  <div>
    <h2>我是用户界面</h2>
    <p>我是用户的相关信息，嘿嘿嘿</p>
    <h2>用户id:{{ usrId }}/{{ $route.params.usrId }}</h2>
    <h2>{{ message }}/{{ msg }}</h2>
  </div>
</template>

<script>
export default {
  name: "Usr",
  data() {
    return {
      message: "你好啊",
    };
  },
  computed: {
    usrId() {
      // 注意，这里是'$route'而不是'$router','$router'可以调用一些方法，它实际上是router/index.js里面导出的最大的router对象
      // $route是当前处于活跃状态的路由。此时拿到的就是:
      // {
      //   path: '/usr/:usrId',
      //   component: Usr,
      // }
      // 这个路由配置对象

      return this.$route.params.usrId; // 这里之所以是usrId，是因为路由配置的path里的最后是usrId。
    },
    msg() {
      return this.message;
    },
  },
};
</script>

<style scoped>
</style>
