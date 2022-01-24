<template>
  <div class="goods-item" @click="itemClick">
    <!--    @load是图片加载完成的vue封装的方法-->
    <img :src="showImage" alt="" @load="imageLoad">
    <div class="goods-info">
      <p>{{ goodsItem.title }}</p>
      <span class="price">{{ goodsItem.price }}</span>
      <span class="collect">{{ goodsItem.cfav }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "GoodsListItem",
  props: {
    goodsItem: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  computed: {
    showImage() {
      return this.goodsItem.image || this.goodsItem.show.img
    }
  },
  methods: {
    // 每张图片加载完都执行该方法
    imageLoad() {
      // 三种解决方案
      // 1.通过$parent.$parent.$scroll.scroll.scroll.refresh().层级关系找
      // 2.通过更改vuex的属性，在home组件里监听vuex的该属性
      // 3.通过事件总线（相当于前台的消息队列）如下：
      this.$bus.$emit('itemImageLoad');

      // 如果不同的组件调用当前组件。那么都会触发home组件里的itemImageLoad()方法。即不是home组件也能让home组件里的bs刷新

      // 解决方案1：按不同的路由来发出不同的事件,不同的组件监听各自的事件，就不会出现跨组件刷新
      // if (this.$route.path.indexOf('/home')) {
      //   this.$bus.$emit('homeItemImageLoad')
      // } else if (this.$route.path.indexOf('/detail')) {
      //   this.$bus.$emit('detailItemImageLoad')
      // }

      // 解决方案2：只有在组件的路由活跃的时候才监听这个事件。不活跃的时候取消监听。详见home组件

    },

    /*跳转到详情页*/
    itemClick() {
      /*动态路由*/
      this.$router.push('/detail/' + this.goodsItem.iid)
      /*query*/
      // this.$router.push({
      //   path: '/detail',
      //   query: {
      //     id: this.goodsItem.iid
      //   }
      // })
    }
  }
}
</script>

<style scoped>
.goods-item {
  padding-bottom: 40px;
  position: relative;
}

.goods-item img {
  width: 100%;
  border-radius: 5px;
}

.goods-info {
  font-size: 12px;
  position: absolute;
  bottom: 5px;
  left: 0;
  right: 0;
  overflow: hidden;
  text-align: center;
}

.goods-info p {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-bottom: 3px;
}

.goods-info .price {
  color: var(--color-high-text);
  margin-right: 20px;
}

.goods-info .collect {
  position: relative;
}

.goods-info .collect::before {
  content: '';
  position: absolute;
  left: -15px;
  top: -1px;
  width: 14px;
  height: 14px;
  background: url("~assets/img/common/collect.svg") 0 0/14px 14px;
}
</style>
