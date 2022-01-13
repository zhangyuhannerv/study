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
    </scroll>
  </div>
</template>

<script>
import DetailNavBar from "./childComps/DetailNavBar";
import DetailSwiper from "./childComps/DetailSwiper";
import DetailBaseInfo from "./childComps/DetailBaseInfo";
import DetailShopInfo from "./childComps/DetailShopInfo";
import {getDetail, Goods, Shop} from "network/detail";

import Scroll from "components/common/scroll/Scroll";

export default {
  name: "Detail",
  components: {
    DetailNavBar,
    DetailSwiper,
    DetailBaseInfo,
    DetailShopInfo,
    Scroll
  },
  data() {
    return {
      iid: null,
      topImages: [],
      goods: {},
      shop: {}
    }
  },
  methods: {},
  created() {
    // 保存传入的iid
    this.iid = this.$route.params.id
    // 根据iid请求详情数据
    getDetail(this.iid).then(res => {
      const data = res.result;
      // 1.获取顶部轮播图数据
      this.topImages = res.result.itemInfo.topImages
      // 2.获取商品信息
      this.goods = new Goods(data.itemInfo, data.columns, data.shopInfo.services)
      // 3.获取店铺信息
      this.shop = new Shop(data.shopInfo)
    })
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
