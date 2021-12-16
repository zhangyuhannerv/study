<template>
  <div id="app">
    <router-view></router-view>
    <tab-bar>
      <tab-bar-item path="/home" activeColor="blue">
        <template v-slot:item-icon>
          <img src="./assets/img/tabbar/home.png" alt="">
        </template>
        <template v-slot:item-icon-active>
          <img src="./assets/img/tabbar/home_active.svg" alt="">
        </template>
        <template v-slot:item-text>
          <div>首页</div>
        </template>
      </tab-bar-item>
      <tab-bar-item path="/about" activeColor="skyblue">
        <template v-slot:item-icon>
          <img src="./assets/img/tabbar/about.png" alt="">
        </template>
        <template v-slot:item-icon-active>
          <img src="./assets/img/tabbar/about_active.svg" alt="">
        </template>
        <template v-slot:item-text>
          <div>关于</div>
        </template>
      </tab-bar-item>
      <tab-bar-item path="/shopcar" activeColor="green">
        <template v-slot:item-icon>
          <img src="./assets/img/tabbar/shopcar.png" alt="">
        </template>
        <template v-slot:item-icon-active>
          <img src="./assets/img/tabbar/shopcar_active.svg" alt="">
        </template>
        <template v-slot:item-text>
          <div>购物车</div>
        </template>
      </tab-bar-item>
      <tab-bar-item path="/profile">
        <template v-slot:item-icon>
          <img src="./assets/img/tabbar/profile.png" alt="">
        </template>
        <template v-slot:item-icon-active>
          <img src="./assets/img/tabbar/profile_active.svg" alt="">
        </template>
        <template v-slot:item-text>
          <div>我的</div>
        </template>
      </tab-bar-item>
    </tab-bar>
  </div>
</template>

<script>
import TabBar from './components/tabbar/TabBar'
import TabBarItem from "./components/tabbar/TabBarItem";

export default {
  name: 'App',
  components: {
    TabBar,
    TabBarItem
  }
}
</script>

<style>
@import "./assets/css/base.css";
</style>
