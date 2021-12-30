module.exports = {
  configureWebpack: {
    resolve: {
      extensions: [],
      // 配置文件夹别名
      alias: {
        // '@': 'src',// 默认配置的别名
        'assets': '@/assets',
        'common': '@/common',
        'components': '@/components',
        'network': '@/network',
        'views': '@/views',
      }
    }
  }
}
