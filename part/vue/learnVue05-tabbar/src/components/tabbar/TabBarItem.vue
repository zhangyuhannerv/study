<template>
  <div class="tab-bar-item" @click="itemClick">
    <!--    动态类或者其他属性不能写在插槽上，因为元素会整体替换插槽-->
    <!--    建议在插槽外面加个div。相关属性写在包裹的div上-->
    <div v-if="!isActive">
      <slot name="item-icon"></slot>
    </div>
    <div v-else>
      <slot name="item-icon-active"></slot>
    </div>
    <div :style="activeStyle">
      <slot name="item-text"></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "TabBarItem",
  props: {
    path: String,
    activeColor: {
      type: String,
      default: 'red'
    }
  },
  computed: {
    isActive() {
      // $router是处于活跃状态的路由对象
      // 下面这种写法能判断/home,/home/....等多种路径
      return this.$route.path.indexOf(this.path) !== -1
    },
    activeStyle() {
      return this.isActive ? {color: this.activeColor} : {}
    }
  },
  data() {
    return {}
  },
  methods: {
    itemClick() {
      this.$router.push(this.path)
    }
  }
}
</script>

<style scoped>
.tab-bar-item {
  flex: 1;
  text-align: center;
  /*ios，安卓的常用高度就是49px*/
  height: 49px;
  font-size: 14px;
}


.tab-bar-item img {
  margin-top: 3px;
  width: 24px;
  height: 24px;
  vertical-align: middle;
  margin-bottom: 2px;
}
</style>
