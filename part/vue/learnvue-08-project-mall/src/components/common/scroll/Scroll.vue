<template>
  <!--ref/children-->
  <!--  ref如果是绑定在组件中，this.$refs.xxx，此时获取到的是组件对象-->
  <!--  ref如果是普通的dom元素上，那么this.$refs.xxx,此时获取到的是元素对象(document.getElementById())-->
  <div ref="wrapper">
    <div>
      <slot></slot>
    </div>
  </div>
</template>

<script>
import BScroll from '@better-scroll/core'
import Pullup from '@better-scroll/pull-up'
import ObserveDOM from '@better-scroll/observe-dom'
import ObserveImage from '@better-scroll/observe-image'

BScroll.use(Pullup)
BScroll.use(ObserveDOM)
BScroll.use(ObserveImage)

export default {
  name: "Scroll",
  data() {
    return {
      scroll: null
    }
  },
  props: {
    probeType: {
      type: Number,
      default: 0
    }
  },
  methods: {
    // 给scrollTo()封上一层，方便外界调用
    // time如果不传那么就会使用默认值300
    scrollTo(x, y, time = 300) {
      this.scroll.scrollTo(x, y, time)
    }
  },
  mounted() {
    // 为了避免用类名获取到别的元素，这里特意使用了$refs
    this.scroll = new BScroll(this.$refs.wrapper, {
      probeType: this.probeType,// 是否实时监听需要传进来一个参数，默认不实时监听(总是实时监听影响性能)
      // pullUpLoad: true,
      // 默认会阻止除了button外的所有dom点击事件。加上click=true才会不阻止
      click: true,
      observeDOM: true, // 开启 observe-dom 插件
      observeImage: true // 开启 observe-image 插件
    })
    //当监听滚动的时候，会发出事件，把当前的参数传递出去
    this.scroll.on('scroll', (position) => {
      this.$emit('scroll', position)
    })
    // this.scroll.on('pullingUp', () => {
    //   console.log('上拉加载更多')
    // })
  }
}
</script>

<style scoped>

</style>
