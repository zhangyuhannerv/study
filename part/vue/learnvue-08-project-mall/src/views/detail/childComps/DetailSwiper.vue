<template>
  <swiper ref="mySwiper" :options="swiperOptions">
    <swiper-slide v-for="item in topImages">
      <div class="swiper-item">
        <img :src="item" width="100%" alt="">
      </div>
    </swiper-slide>
    <div class="swiper-pagination" slot="pagination"></div>
  </swiper>
</template>

<script>
import {Swiper, SwiperSlide, directive} from 'vue-awesome-swiper'

// import style (<= Swiper 5.x)
import 'swiper/css/swiper.css'

export default {
  name: 'DetailSwiper',
  props: {
    topImages: {
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
    }
  },
  methods: {},
  computed: {
    swiper() {
      return this.$refs.mySwiper.$swiper
    }
  },
}
</script>

<style scoped>
.swiper-item {
  height: 300px;
  overflow: hidden;
}
</style>
