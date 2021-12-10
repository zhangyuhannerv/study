<template>
  <div>
    <!--在某些情况下，一个页面的路径path可能是不确定的,比如我们进入用户页面时，希望是如下的路径-->
    <!--  /usr/aaa,/usr/bbb。除了/use外，还有用户的id(或者用户名)-->
    <!--  这种path和component的匹配关系，我们称之为动态路由(也是路由传递参数的一种方式)-->

    <!--如果在路由里配置的是/usr/:usrId，那么这里匹配不到任何的组件-->
    <router-link to="/usr">用户界面1（匹配不到）</router-link>
    <!--    必须在原有的路径后面拼接别用户id才能匹配到-->
    <router-link to="/usr/zhangsan">用户界面2（匹配的到）</router-link>
    <!--    ''表示真实的字符串，usrId是变量-->
    <!-- 如果path后面不传值的话，那么就会匹配不到路由，整个组件就渲染不出来 -->
    <router-link :to="'/usr/' + usrId"
      >用户界面3（使用data里面的属性）</router-link
    >
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "DynamicRouteApp",
  data() {
    return {
      usrId: "李四",
    };
  },
};
</script>

<style scoped>
</style>
