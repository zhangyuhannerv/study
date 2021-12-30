<template>
  <div class="block">
    <el-carousel trigger="click">
      <el-carousel-item v-for="item in banners">
        <a :href="item.link">
          <img :src="item.image" style="width: 100%" alt="">
        </a>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
/*使用element-ui封装*/
export default {
  name: "HomeSwiper1",
  props: {
    banners: {
      type: Array,
      default() {
        return []
      }
    }
  }
}
</script>

<style scoped>

</style>
