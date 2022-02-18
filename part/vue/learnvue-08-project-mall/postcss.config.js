module.exports = {
  plugins: {
    autoprefixer: {},
    'postcss-px-to-viewport': {
      viewportWidth: 375,// 视窗的宽度，对应的是我们设计稿的宽度
      viewportHeight: 667,// 视窗的高度，对应的是我们设计稿的高度（也可以不配置）
      unitPrecision: 5,// 指定'px'转换为视窗单位的最小位数（很多时候无法整除）
      viewportUnit: 'vw',// 指定需要转换的视窗单位，建议使用vw
      // selectorBlackList: ['ignore', 'tab-bar', 'tab-bar-item'],// 指定不需要转换的类，后面再讲
      selectorBlackList: ['ignore'],// 指定不需要转换的类
      minPixelValue: 1,// 小于或者等于'1px'不转换为视窗单位
      mediaQuery: false,// 允许在媒体查询中转换px
      // exclude: [/^(TabBar.vue)$/, /^(TabBarItem.vue)$/],// 哪些文件不需要转换,不知道为什么不生效。可能原因，插槽里插入的东西属于另外一个文件
      // exclude: [/TabBar/],// 这样修改就生效了，此时正则匹配到了三个文件(TabBar.vue，TabBarItem.vue，MainTabBar.vue)
    }
  }
}
