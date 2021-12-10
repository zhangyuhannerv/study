<template>
  <div>
    <!-- <router-link to="/profile">档案</router-link> -->
    <!-- 演示使用query的方式在路由之间传递参数 -->
    <!-- 路径变成了:http://localhost:8080/profile?name=张雨晗&age=18&height=170 -->
    <router-link
      :to="{
        path: '/profile',
        query: {
          name: '张雨晗',
          age: 18,
          height: 170,
        },
      }"
      >档案</router-link
    >

    <!-- 这是动态路由的传参方式 -->
    <router-link to="/usr/zhangsan">用户</router-link>

    <!-- 下面是通过代码实现上面两种的传递参数的方式 -->
    <button @click="profileClick">档案</button>
    <button @click="usrClick">用户</button>

    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "QueryTransParam",
  data() {
    return {
      usrId: "李四",
      person: {
        name: "haiyi",
        age: 16,
        height: 160,
      },
    };
  },
  methods: {
    usrClick() {
      this.$router.push("/usr/" + this.usrId);
    },
    profileClick() {
      this.$router.push({
        path: "/profile",
        query: this.person,
      });
    },
  },
};
</script>

<style>
</style>