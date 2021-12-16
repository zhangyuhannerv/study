<template>
  <div>
    <h2>我是profile文件</h2>
    <h2>{{ $route.query }}</h2>
    <h2>{{ $route.query.name }}</h2>
    <h2>{{ $route.query.age }}</h2>
    <h2>{{ $route.query.height }}</h2>
  </div>
</template>

<script>
export default {
  name: "Profile",
  created() {
    console.log("Profile created"); // 组件创建
    // 这里可以改变当前网页的标题
    // document.title = "关于";
  },
  destroyed() {
    console.log("Profile destroyed");
  }
};
</script>

<style>
</style>
