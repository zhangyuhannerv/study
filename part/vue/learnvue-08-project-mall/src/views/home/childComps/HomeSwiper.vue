<template>
  <swiper>
    <swiper-item v-for="item in banners">
      <template>
        <a :href="item.link">
          <img :src="item.image" alt="">
        </a>
      </template>
    </swiper-item>
  </swiper>
</template>

<script>
import {Swiper, SwiperItem} from 'components/common/swiper'

/*老师封装的swiper*/
export default {
  name: "HomeSwiper",
  props: {
    banners: {
      type: Array,
      default() {
        return []
      }
    }
  },
  components: {
    Swiper,
    SwiperItem
  },
}
</script>

<style scoped>

</style>
