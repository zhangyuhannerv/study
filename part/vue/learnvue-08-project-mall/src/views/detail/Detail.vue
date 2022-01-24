<template>
  <div id="detail">
    <detail-nav-bar class="detail-nav"/>
    <scroll
      ref="scroll"
      class="content"
    >
      <detail-swiper :top-images="topImages"></detail-swiper>
      <detail-base-info :goods="goods"></detail-base-info>
      <detail-shop-info :shop="shop"></detail-shop-info>
      <detail-images-info :images-info="detailInfo" @imgLoad="imgLoad"></detail-images-info>
      <detail-param-info :param-info="paramInfo"></detail-param-info>
      <detail-comment-info :comment-info="commentInfo"></detail-comment-info>
      <goods-list :goods="recommends"></goods-list>
    </scroll>
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

import {getDetail, getRecommend, Goods, Shop, GoodsParams} from "network/detail";
// import {debounce} from "common/util";
import {itemImageListenerMixin} from "common/mixin";

import Scroll from "components/common/scroll/Scroll";
import GoodsList from "components/content/Goods/GoodsList";


export default {
  name: "Detail",
  components: {
    GoodsList,
    DetailNavBar,
    DetailSwiper,
    DetailBaseInfo,
    DetailShopInfo,
    Scroll,
    DetailImagesInfo,
    DetailParamInfo,
    DetailCommentInfo,
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
    }
  },
  methods: {
    imgLoad() {
      this.$refs.scroll.refresh()
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
    })
    // 请求推荐数据
    getRecommend().then(res => {
      this.recommends = res.data.list
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
  mixins: [itemImageListenerMixin],
  // 这里和home组件不同的地方在于，detail组件没有缓存，所以取消监听不是在deactivated里而是在destroyed里
  destroyed() {
    this.$bus.$off('itemImageLoad', this.itemImageListener)
  },
  updated() {
    // 这里不重新刷新，就不能滚动，我也不知道为啥
    this.$refs.scroll.refresh()
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
