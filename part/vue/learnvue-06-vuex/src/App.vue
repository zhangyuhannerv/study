<template>
  <div id="app">
    <h2>{{ message }}</h2>
    <h2>{{ counter }}</h2>
    <button @click="counter++">+</button>
    <button @click="counter--">-</button>
    <hr />
    <!--    注意：不要像下面那样直接修改state,而是要按照官方-->
    <!--    文档说的那样通过mutation来修改state-->
    <!--    原因：直接修改state，devtools无法跟踪到是哪个组件在执行相关操作-->
    <!--    如果，state修改错误，那么排错定位不到-->
    <button @click="$store.state.shareCounter++">+</button>
    <button @click="$store.state.shareCounter--">-</button>
    <button @click="addtion">通过mutation修改counter+</button>
    <button @click="subtraction">通过mutation修改counter-</button>

    <!-- mutation提交携带参数 -->
    <button @click="addCount(5)">+5</button>
    <button @click="addCount(10)">+10</button>
    <button @click="addStudent">增加学生</button>
    <h2>{{ $store.state.shareCounter }}</h2>
    <!--    如果想要获得shareCounter的平方-->
    <!--    这种写法也可以，但是一它很丑，二它不会缓存结果，有一个重新计算一遍，三多个组件用，多个组件要写重复的很丑的代码-->
    <h2>{{ $store.state.shareCounter * $store.state.shareCounter }}</h2>
    <!--    通过vuex的getters来获取-->
    <h2>{{ $store.getters.powSharedCounter }}</h2>
    <hr />
    <hello-vuex :counter="counter"></hello-vuex>
  </div>
</template>

<script>
import HelloVuex from "./components/HelloVuex";

export default {
  name: "App",
  data() {
    return {
      message: "我是app组件",
      counter: 0,
    };
  },
  methods: {
    addtion() {
      this.$store.commit("increment");
    },
    subtraction() {
      this.$store.commit("decrement");
    },
    // 修改state通过commit,并携带参数
    // 形如下面的参数count或者stu，在术语上称之为mutation的负载（载荷payload)。
    // 如果参数不是一个的话，payload也可以是一个对象，这个时候在对象中提取相关的参数
    addCount(count) {
      // 1.下面是普通的提交风格，除了这种普通的提交风格之外，vue还提供了一种对象的提交风格
      // this.$store.commit("incrementCount", count);

      // 2.对象的提交风格
      this.$store.commit({
        type: "incrementCount", // type就是提交类型（事件类型）
        // 其他的多个参数可以依次罗列在下面，但是注意，事件类型里接收参数的方式和普通的提交方式不太一样。详见store里对应的commit
        count,
        age: 18,
      });
    },
    addStudent() {
      let stu = { id: 114, name: "alan", age: 35 };
      this.$store.commit("addStudent", stu);
    },
  },
  components: {
    HelloVuex,
  },
};
</script>

<style>
</style>
