<template>
  <div class="cart">
    <nav-bar class="nav-bar">
      <template v-slot:center>
        <div>
          <!--          购物车({{ cartLength }})-->
          购物车({{ length }})
        </div>
      </template>
    </nav-bar>
  </div>
</template>

<script>
import NavBar from "components/common/navbar/NavBar";

// mapGetters是vuex的辅助函数，能将getters里的属性混入到组件的computed里
import {mapGetters} from 'vuex'

export default {
  name: "Shopcar",
  components: {
    NavBar
  },
  computed: {
    // 使用对象展开运算符将 getter 混入 computed 对象中
    // 语法1：原名称混入
    // ...mapGetters([
    //   'cartLength',
    //   'cartList',
    // ]),

    // 语法2：映射混入
    ...mapGetters({
      length: 'cartLength',
      list: 'cartList',
    })
  }
}
</script>

<style scoped>
.nav-bar {
  background-color: var(--color-tint);
  color: #fff;
}
</style>
