<template>
  <div id="detail">
    <!-- 事件这里和html的属性不一样。发出什么，直接写发出的事件即可 -->
    <!-- 如果发出titleClick，那么就@titleClick   -->
    <detail-nav-bar class="detail-nav" @titleClick="titleClick" ref="nav"/>
    <scroll
      ref="scroll"
      class="content"
      :probe-type="3"
      @scroll="contentScroll"
    >
      <!--   在html中，标签的属性是不区分大小写的    -->
      <!--   属性名称：topImages；传入值的时候：top-images   -->
      <detail-swiper :top-images="topImages"></detail-swiper>
      <detail-base-info :goods="goods"></detail-base-info>
      <detail-shop-info :shop="shop"></detail-shop-info>
      <detail-images-info :images-info="detailInfo" @imgLoad="imgLoad"></detail-images-info>
      <detail-param-info ref="params" :param-info="paramInfo"></detail-param-info>
      <detail-comment-info ref="comment" :comment-info="commentInfo"></detail-comment-info>
      <goods-list ref="recommend" :goods="recommends"></goods-list>
    </scroll>
    <detail-bottom-bar/>
    <back-top @click.native="backTop" v-show="isShowBackTop"/>
  </div>
</template>

<script>
import DetailNavBar from "./childComps/DetailNavBar";
import DetailSwiper from "./childComps/DetailSwiper";
import DetailBaseInfo from "./childComps/DetailBaseInfo";
import DetailShopInfo from "./childComps/DetailShopInfo";
import DetailImagesInfo from "./childComps/DetailImagesInfo";
import DetailParamInfo from "./childComps/DetailParamInfo";
import DetailCommentInfo from "./childComps/DetailCommentInfo";
import DetailBottomBar from "./childComps/DetailBottomBar";

import {getDetail, getRecommend, Goods, Shop, GoodsParams} from "network/detail";
import {debounce} from "common/util";
import {itemImageListenerMixin, backTopMixin} from "common/mixin";

import Scroll from "components/common/scroll/Scroll";
import GoodsList from "components/content/Goods/GoodsList";
import BackTop from "../../components/content/backTop/BackTop";


export default {
  name: "Detail",
  components: {
    BackTop,
    GoodsList,
    DetailNavBar,
    DetailSwiper,
    DetailBaseInfo,
    DetailShopInfo,
    Scroll,
    DetailImagesInfo,
    DetailParamInfo,
    DetailCommentInfo,
    DetailBottomBar
  },
  data() {
    return {
      iid: null,
      topImages: [],
      goods: {},
      shop: {},
      detailInfo: {},
      paramInfo: {},
      commentInfo: {},
      recommends: [],
      themeTopYs: [],
      getThemeTopY: null,
      currentIndex: 0
    }
  },
  methods: {
    imgLoad() {
      // 旧
      // this.$refs.scroll.refresh()

      // 新
      this.scrollRefresh()

      // 每当图片加载完都获取一下四个组件的offsetTop
      // 但是这样执行次数太高了，所以用防抖函数封一下后，执行防抖函数
      this.getThemeTopY()
    },
    titleClick(index) {
      // console.log(index)
      this.$refs.scroll.scrollTo(0, -this.themeTopYs[index])
    },
    contentScroll(position) {
      // 1.获取y值
      const positionY = -position.y

      // 2.把y和themeTopYs中的值进行对比
      // 获得高度数组的长度
      const len = this.themeTopYs.length
      for (let i = 0; i < len - 1; i++) {
        // 注意(let i in arr)的遍历i是字符串。所以i+1可能是01，21，31...
        // 这里要做个转化(或者写成最基础的for循环)
        // i = Number(i)


        // 这种写法面临着数组越界的问题
        // if (positionY > this.themeTopYs[i] && position < this.themeTopYs[i + 1]) {
        //   console.log(i);
        // }


        // 修改后的写法1(普通写法)
        // currentIndex的引入主要是为了防止在同一个组件内滚动时频繁触发title改变(这些改变都是相同的值，都是无意义的)的事件
        // if (this.currentIndex !== i && ((i < len - 1 && positionY >= this.themeTopYs[i] && positionY < this.themeTopYs[i + 1])
        //   || (i === len - 1 && positionY >= this.themeTopYs[i]))) {
        //   this.currentIndex = i;
        //
        // }

        // 修改后的写法2(heck写法)
        // 思路，在themeTopYs的最后加个最大值(Number.MAX_VALUE)。这样不用在分情况判断了。i从0循环到len-1即可。
        // 这种写法，虽然多占了一点内存空间（原数组多存了个数字最大值），但是判断条件精简了。代码执行的效率更高，性能更强。推荐这种写法。
        // 这是一种典型的空间换时间的思想(多的内存空间->少的执行时间)
        if (this.currentIndex !== i && (positionY >= this.themeTopYs[i] && positionY < this.themeTopYs[i + 1])) {
          this.currentIndex = i;
        }

        // 正常在进入到条件判断里后可以直接给子组件里的currentIndex赋值
        // 但是为了熟悉watch
        // 所以加了个watch,监视currentIndex的变化。在watch里写回调函数重新赋值子组件的currentIndex

        // 3.判断回到顶部是否显示
        this.listenShowBackTop(positionY)
      }
    }
  },
  created() {
    // 保存传入的iid
    this.iid = this.$route.params.id
    // this.iid = this.$route.query.id(通过query传参的接收方法)
    // 根据iid请求详情数据
    getDetail(this.iid).then(res => {
      const data = res.result;
      // 1.获取顶部轮播图数据
      this.topImages = res.result.itemInfo.topImages
      // 2.获取商品信息
      this.goods = new Goods(data.itemInfo, data.columns, data.shopInfo.services)
      // 3.获取店铺信息
      this.shop = new Shop(data.shopInfo)
      // 4.保存商品的详情数据
      this.detailInfo = data.detailInfo
      // 5.获取参数信息
      this.paramInfo = new GoodsParams(data.itemParams.info, data.itemParams.rule)
      // 6.取出评论信息
      if (data.rate.cRate !== 0) {
        this.commentInfo = data.rate.list[0]
      }

      // 第一次获取offsetTop:
      // 如果在data获取之后紧接着就获取各个部分的offsetTop，此时的值可能获取为undefined或者错误的值
      // 原因：this.$refs.params.$el压根还没有渲染或者没有渲染完
      // this.themeTopYs = []// 每次更新都先置为空
      // this.themeTopYs.push(0)
      // this.themeTopYs.push(this.$refs.params.$el.offsetTop)// 参数的offsetTop
      // this.themeTopYs.push(this.$refs.comment.$el.offsetTop)// 评论的offsetTop
      // this.themeTopYs.push(this.$refs.recommend.$el.offsetTop)// 推荐的offsetTop
      // console.log(this.themeTopYs)


      // 第二次获取offsetTop:
      // $nextTick()是一个内部函数。
      // 它是根据最新的数据，对应的dom渲染完之后回调一个函数
      // 但是这里也仅仅是是把dom渲染出来了，组件内部的图片还没有渲染完
      // 即目前获得的offsetTop是不包含图片的
      // 所以一般情况下，offset的值不对，都是因为图片的问题
      // this.$nextTick(() => {
      //   // 获得各个模块距离顶部的高度
      //   this.themeTopYs = []// 每次更新都先置为空
      //   this.themeTopYs.push(0)
      //   this.themeTopYs.push(this.$refs.params.$el.offsetTop)// 参数的offsetTop
      //   this.themeTopYs.push(this.$refs.comment.$el.offsetTop)// 评论的offsetTop
      //   this.themeTopYs.push(this.$refs.recommend.$el.offsetTop)// 推荐的offsetTop
      //
      //   console.log(this.themeTopYs)
      // })

      // 第三次获取offsetTop请看imgLoad()

    })
    // 请求推荐数据
    getRecommend().then(res => {
      this.recommends = res.data.list
    })
    // 给getThemeTopY赋值（对给this.themeTopYs赋值的操作进行防抖）
    this.getThemeTopY = debounce(() => {
      this.themeTopYs = []// 每次更新都先置为空
      this.themeTopYs.push(0)
      this.themeTopYs.push(this.$refs.params.$el.offsetTop)// 参数的offsetTop
      this.themeTopYs.push(this.$refs.comment.$el.offsetTop)// 评论的offsetTop
      this.themeTopYs.push(this.$refs.recommend.$el.offsetTop)// 推荐的offsetTop
      this.themeTopYs.push(Number.MAX_VALUE)// heck写法
      // console.log(this.themeTopYs)
    })
  },
  mounted() {

    // 防抖和图片加载完成的bs刷新事件和home组件里的代码重复，所以放在混入里了
    /*    const refresh = debounce(this.$refs.scroll.refresh)
        // 必须在Scroll组件初始化之后（即mounted里）做监听
        // 监听goodItem里的图片加载完成
        // 对监听的事件进行保存
        this.itemImageListener = () => {
          // this.$refs.scroll.refresh()
          // console.log('监听到要执行refresh的次数')
          // 对于refresh()非常频繁的问题进行防抖操作。debounce：防抖/throttle：节流
          refresh()
        }
        this.$bus.$on('itemImageLoad', this.itemImageListener)*/
  },
  mixins: [itemImageListenerMixin, backTopMixin],
  // 这里和home组件不同的地方在于，detail组件没有缓存，所以取消监听不是在deactivated里而是在destroyed里
  destroyed() {
    this.$bus.$off('itemImageLoad', this.itemImageListener)
  },
  updated() {
    // 这里不重新刷新，就不能滚动，我也不知道为啥
    // 加上了图片加载完的监听后，这里的代码也能重新滚动了...无语
    // this.$refs.scroll.refresh()


    // 获得各个模块距离顶部的高度
    // 这里和$nextTick()一样，也仅仅是把dom渲染出来了，组件内部的图片还没有渲染完
    // 即目前获得的offsetTop是不包含图片的
    // this.themeTopYs = []// 每次更新都先置为空
    // this.themeTopYs.push(0)
    // this.themeTopYs.push(this.$refs.params.$el.offsetTop)// 参数的offsetTop
    // this.themeTopYs.push(this.$refs.comment.$el.offsetTop)// 评论的offsetTop
    // this.themeTopYs.push(this.$refs.recommend.$el.offsetTop)// 推荐的offsetTop

    // console.log(this.themeTopYs)
  },
  watch: {
    currentIndex(newValue) {
      this.$refs.nav.currentIndex = newValue
    }
  }
}
</script>

<style scoped>
#detail {
  /*盖注底下的tab-bar*/
  position: relative;
  z-index: 9;
  background-color: #fff;

  height: 100vh;
}

.content {
  height: calc(100% - 93px);
  overflow: hidden;
}

</style>
