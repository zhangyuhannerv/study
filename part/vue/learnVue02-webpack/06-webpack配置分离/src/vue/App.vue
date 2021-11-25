<template>
  <div>
    <h2 class="title">{{ message }}</h2>
    <h2>{{ name }}</h2>
    <button @click="btnClick">按钮</button>
    <Cpn></Cpn>
  </div>
</template>

<script>
// 注意：这里不写.vue后缀会报错
// import Cpn from "./Cpn.vue";

// 如果不想写.vue的后缀，那么需要更改webpack的配置(extensions)
// 更改完后
import Cpn from "./Cpn";

export default {
  name: 'App',// 这是组件的名字，因为是export default的关系，引入的时候可以自己名命。但是最好还是用这里定义的名字
  // template: `
  //   <div>
  //   <h2>{{ message }}</h2>
  //   <h2>{{ name }}</h2>
  //   <button @click="btnClick">按钮</button>
  //   </div>`,
  data() {
    return {
      message: 'hello webpack',
      name: '张三'
    }
  },
  methods: {
    btnClick() {
      console.log("111");
    }
  },
  components: {
    Cpn,
  }
}
</script>

<style scoped>
.title {
  color: skyblue;
}
</style>
