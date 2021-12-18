<template>
  <tab-bar>
    <tab-bar-item path="/home" activeColor="blue">
      <template v-slot:item-icon>
        <!--标签的src属性在使用自己定义的目录别名的时候，前面必须加个`~`-->
        <img src="~assets/img/tabbar/home.png" alt="">
      </template>
      <template v-slot:item-icon-active>
        <img src="~assets/img/tabbar/home_active.svg" alt="">
      </template>
      <template v-slot:item-text>
        <div>首页</div>
      </template>
    </tab-bar-item>
    <tab-bar-item path="/about" activeColor="skyblue">
      <template v-slot:item-icon>
        <img src="@/assets/img/tabbar/about.png" alt="">
      </template>
      <template v-slot:item-icon-active>
        <img src="@/assets/img/tabbar/about_active.svg" alt="">
      </template>
      <template v-slot:item-text>
        <div>关于</div>
      </template>
    </tab-bar-item>
    <tab-bar-item path="/shopcar" activeColor="green">
      <template v-slot:item-icon>
        <img src="@/assets/img/tabbar/shopcar.png" alt="">
      </template>
      <template v-slot:item-icon-active>
        <img src="@/assets/img/tabbar/shopcar_active.svg" alt="">
      </template>
      <template v-slot:item-text>
        <div>购物车</div>
      </template>
    </tab-bar-item>
    <tab-bar-item path="/profile">
      <template v-slot:item-icon>
        <img src="@/assets/img/tabbar/profile.png" alt="">
      </template>
      <template v-slot:item-icon-active>
        <img src="@/assets/img/tabbar/profile_active.svg" alt="">
      </template>
      <template v-slot:item-text>
        <div>我的</div>
      </template>
    </tab-bar-item>
  </tab-bar>
</template>

<script>
// 这里webpack已经把src起了个别名@
import TabBar from '@/components/tabbar/TabBar'
// 自己又加了个别名components指向'src/components'
import TabBarItem from "components/tabbar/TabBarItem";

export default {
  name: "MainTabBar",
  components: {
    TabBar,
    TabBarItem
  }
}
</script>

<style scoped>

</style>
