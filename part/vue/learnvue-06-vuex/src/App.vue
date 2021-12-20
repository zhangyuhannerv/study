<template>
  <div id="app">
    <h2>{{ message }}</h2>
    <h2>{{ counter }}</h2>
    <button @click="counter++">+</button>
    <button @click="counter--">-</button>
    <hr>
    <!--    注意：不要像下面那样直接修改state,而是要按照官方-->
    <!--    文档说的那样通过mutation来修改state-->
    <!--    原因：直接修改state，devtools无法跟踪到是哪个组件在执行相关操作-->
    <!--    如果，state修改错误，那么排错定位不到-->
    <button @click="$store.state.shareCounter++">+</button>
    <button @click="$store.state.shareCounter--">-</button>
    <button @click="addtion">通过mutation修改counter+</button>
    <button @click="subtraction">通过mutation修改counter-</button>
    <h2>{{ $store.state.shareCounter }}</h2>
    <!--    如果想要获得shareCounter的平方-->
    <!--    这种写法也可以，但是一它很丑，二它不会缓存结果，有一个重新计算一遍，三多个组件用，多个组件要写重复的很丑的代码-->
    <h2>{{ $store.state.shareCounter * $store.state.shareCounter }}</h2>
    <!--    通过vuex的getters来获取-->
    <h2>{{ $store.getters.powSharedCounter }}</h2>
    <hr>
    <hello-vuex :counter="counter"></hello-vuex>
  </div>
</template>

<script>
import HelloVuex from "./components/HelloVuex";

export default {
  name: 'App',
  data() {
    return {
      message: '我是app组件',
      counter: 0,
    }
  },
  methods: {
    addtion() {
      this.$store.commit('increment')
    },
    subtraction() {
      this.$store.commit('decrement')
    }
  },
  components: {
    HelloVuex
  }
}
</script>

<style>

</style>
