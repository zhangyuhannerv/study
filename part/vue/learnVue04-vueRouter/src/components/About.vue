<template>
  <div>
    <h2>我是关于</h2>
    <p>我是关于内容，呵呵呵</p>
  </div>
</template>

<script>
export default {
  name: "about",
  created() {
    console.log("about created"); // 组件创建
    // 这里可以改变当前网页的标题
    // document.title = "关于";
  },
  destroyed() {
    console.log("about destroyed");
  }
};
</script>

<style scoped>
</style>
