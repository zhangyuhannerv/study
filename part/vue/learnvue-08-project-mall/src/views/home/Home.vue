<template>
  <div id="home">
    <nav-bar class="home-nav">
      <template v-slot:center>
        <div>
          购物街
        </div>
      </template>
    </nav-bar>

    <!--    注意计算属性使用驼峰时，属性需要用-连接-->
    <!--    当不是监听原生事件时，无需加上native修饰符-->
    <scroll
      class="content"
      ref="scroll"
      @scroll="contentScroll"
      :probe-type="3"
      :pull-up-load="true"
      @pullingUp="loadMore">
      <!--    <home-swiper :banners="banners"></home-swiper>-->
      <!--    <home-swiper1 :banners="banners"></home-swiper1>-->
      <home-swiper2 :banners="banners"></home-swiper2>
      <home-recommend :recommends="recommends"></home-recommend>
      <feature-view/>
      <tab-control :titles="['流行','新款','精选']"
                   class="tab-control"
                   @tabClick="tabClick"></tab-control>
      <goods-list :goods="showGoods"></goods-list>
    </scroll>

    <!--    组件能否直接监听点击事件呢？-->
    <!--    不能，需要使用修饰符native-->
    <!--    native:监听组件根元素的原生事件-->
    <!--    native当我们需要监听组件的原生事件时，必须给对应的事件加上.native才能继续监听-->
    <back-top @click.native="backTop" v-show="isShowBackTop"/>
  </div>
</template>

<script>
/*子组件*/
import HomeSwiper from "./childComps/HomeSwiper";
import HomeSwiper1 from "./childComps/HomeSwiper1";
import HomeSwiper2 from "./childComps/HomeSwiper2";
import HomeRecommend from "./childComps/HomeRecommend";
import FeatureView from "./childComps/FeatureView";

/*公共组件*/
import NavBar from "components/common/navbar/NavBar";
import TabControl from "components/content/tabControl/TabControl";
import GoodsList from "components/content/Goods/GoodsList";
import Scroll from "components/common/scroll/Scroll";
import BackTop from "components/content/backTop/BackTop";

/*网络请求*/
import {
  getHomeMultiData,
  getHomeGoods
} from "network/home";

export default {
  name: "Home",
  components: {
    NavBar,
    HomeSwiper,
    HomeSwiper1,
    HomeSwiper2,
    HomeRecommend,
    FeatureView,
    TabControl,
    GoodsList,
    Scroll,
    BackTop
  },
  data() {
    return {
      banners: [],
      recommends: [],
      goods: {
        'pop': {page: 0, list: []},
        'new': {page: 0, list: []},
        'sell': {page: 0, list: []},
      },
      currentType: 'pop',
      isShowBackTop: false
    }
  },
  computed: {
    showGoods() {
      return this.goods[this.currentType].list
    }
  },
  created() {
    // create里一般不写具体业务，只写执行了哪些方法。
    // 1.请求多个数据
    this.getHomeMultiData()
    // 2.请求商品数据
    this.getHomeGoods('pop')
    this.getHomeGoods('new')
    this.getHomeGoods('sell')
  },
  methods: {
    /**
     * 事件监听
     */
    tabClick(index) {
      switch (index) {
        case 0:
          this.currentType = 'pop'
          break
        case 1:
          this.currentType = 'new'
          break
        case 2:
          this.currentType = 'sell'
          break
      }
    },

    backTop() {
      // 父组件访问子组件
      // 获取子组件里的scroll属性
      // 使用原生的方法
      // this.$refs.scroll.scroll.scrollTo(0, 0, 500);// 加上缓动效果，500ms内回到顶部
      // 使用组件内部封装的方法
      this.$refs.scroll.scrollTo(0, 0, 500)
    },

    contentScroll(position) {
      // position.y是负值
      this.isShowBackTop = -position.y > 1000
    },


    loadMore() {
      this.getHomeGoods(this.currentType)
    },

    /**
     * 网络请求
     */
    getHomeMultiData() {
      getHomeMultiData().then(res => {
        this.banners = res.data.banner.list
        this.recommends = res.data.recommend.list
      })
    },
    getHomeGoods(type) {
      const page = this.goods[type].page + 1;
      getHomeGoods(type, page).then(res => {
        // this.goods[type].list.concat(res.data.list)// 不知道为什么concat不行
        this.goods[type].list.push(...res.data.list)// 这里只能用es6的解构
        this.goods[type].page++;// 将当前页码加1
        this.$refs.scroll.finishPullUp()
      })
    }
  }
}
</script>

<style scoped>
#home {
  height: 100vh;
  position: relative;
  /*隐藏原先自带的滚动条*/
  overflow: hidden;
}

.home-nav {
  background: var(--color-tint);
  color: #fff;
  position: fixed;
  left: 0;
  top: 0;
  right: 0;
  z-index: 9;
}

/*目前先简单的使用css实现tab-control向下滚动时吸附在最上面的功能*/
/*注意：sticky的兼容性不好，移动端一般都适配，但是如果要适配pc端的各种浏览器，那么该属性最好别用*/
/*后续会用better-scroll来替换掉下面的实现方式*/
.tab-control {
  position: sticky;
  top: 44px;
  z-index: 9;
}

/*第一种确定中间内容的样式*/
/*.content {
  margin-top: 44px;
  height: calc(100% - 93px);
}*/

/*第二种确定中间内容的样式，这里选择第二种*/
.content {
  position: absolute;
  top: 44px;
  bottom: 49px;
  left: 0;
  right: 0;
}

</style>
