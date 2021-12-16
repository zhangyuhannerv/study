<template>
  <div>
    <h2>我是用户界面</h2>
    <p>我是用户的相关信息，嘿嘿嘿</p>
    <h2>用户id:{{ usrId }}/{{ $route.params.usrId }}</h2>
    <h2>{{ message }}/{{ msg }}</h2>
    <button @click="btnClick">打印router对象</button>
    <button @click="btnClick1">打印route对象</button>
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
  methods: {
    btnClick() {
      console.log(this.$router);
      // 和main里打印的router对象做对比之后，
      // 结论：任何组件里的this.$router就是路由配置index.js里面定义的最大的router
      // 都是一样的
    },
    btnClick1() {
      console.log(this.$route);
      // 结论:得到的是。当前处于活跃的路由对象。vue会自动的向里面添加一些其他的属性
      // {
      //   path: '/usr/:usrId',
      //   component: Usr,
      // },
    },

    // 之所以会得到上面的结果，是因为所有的组件都继承自Vue类的原型
  },
  computed: {
    usrId() {
      // 注意，这里是'$route'而不是'$router','$router'可以调用一些方法，它实际上是router/index.js里面导出的最大的router对象
      // $route是当前处于活跃状态的路由对象。此时拿到的就是:
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
  created() {
    console.log("user created");
    // 测试如果在vue原型上加个test(),那么当前组件是否有test()
    this.test(); // 结论：控制台打印'test'。执行test()成功
  },
  destroyed() {
    console.log("user destroyed");
  }
};
</script>

<style scoped>
</style>
