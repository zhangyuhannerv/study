<template>
  <swiper ref="mySwiper" :options="swiperOptions">
    <swiper-slide v-for="item in banners">
      <img :src="item.image" width="100%" alt="" @load="imageLoad">
    </swiper-slide>
    <div class="swiper-pagination" slot="pagination"></div>
  </swiper>
</template>

<script>
import {Swiper, SwiperSlide, directive} from 'vue-awesome-swiper'

// import style (<= Swiper 5.x)
import 'swiper/css/swiper.css'

export default {
  name: 'HomeSwiper2',
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
    SwiperSlide
  },
  directives: {
    swiper: directive
  },
  data() {
    return {
      swiperOptions: {
        pagination: {
          el: '.swiper-pagination'
        },
        loop: true,
        autoplay: {
          disableOnInteraction: false,
          delay: 1500
        }
        // Some Swiper option/callback...
      },
      isLoad: false
    }
  },
  methods: {
    // 只需要图片加载一次就能获得高度了
    // 通过isLoad来只发射一次事件
    imageLoad() {
      if (!this.isLoad) {
        this.$emit('swiperImageLoad')
        this.isLoad = true
      }
    }
  },
  computed: {
    swiper() {
      return this.$refs.mySwiper.$swiper
    }
  },
  // mounted() {
  // console.log('Current Swiper instance object', this.swiper)
  // this.swiper.slideTo(0, 1000, false)
  // }
}
</script>
