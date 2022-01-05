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
  mounted() {
    // 为了避免用类名获取到别的元素，这里特意使用了$refs
    this.scroll = new BScroll(this.$refs.wrapper, {
      probeType: 2,
      pullUpLoad: true,
      // 默认会阻止除了button外的所有dom点击事件。加上click=true才会不阻止
      click: true,
      observeDOM: true, // 开启 observe-dom 插件
      observeImage: true // 开启 observe-image 插件
    })
    this.scroll.on('scroll', (position) => {
      // console.log(position)
    })
    // this.scroll.on('pullingUp', () => {
    //   console.log('上拉加载更多')
    // })
  }
}
</script>

<style scoped>

</style>
