/*混入：具体的请看vue2文档*/
import {debounce} from "./util";

/**
 * 所有引用该mixin对象的组件实例的mounted上都会多出下面一段代码
 * @type {{mounted(): void}}
 */
export const itemImageListenerMixin = {
  components: {},
  methods: {},
  data() {
    return {
      itemImageListener: null,
      scrollRefresh: null,
    }
  },
  mounted() {
    this.scrollRefresh = debounce(this.$refs.scroll.refresh)
    // 必须在Scroll组件初始化之后（即mounted里）做监听
    // 监听goodItem里的图片加载完成
    // 对监听的事件进行保存
    this.itemImageListener = () => {
      // this.$refs.scroll.refresh()
      // console.log('监听到要执行refresh的次数')
      // 对于refresh()非常频繁的问题进行防抖操作。debounce：防抖/throttle：节流
      this.scrollRefresh()
    }
    this.$bus.$on('itemImageLoad', this.itemImageListener)

    console.log('mixin log')
  },
}
