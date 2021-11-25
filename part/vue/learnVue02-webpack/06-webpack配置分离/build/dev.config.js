// 开发时配置
const webpackMerge = require('webpack-merge')
const baseConfig = require('./base.config')


module.exports = webpackMerge(baseConfig, {
  // webpack本地服务器搭建，用来热部署前端项目,它实际上是把打包后的文件放到了内存中，而不是放到dist里去
  devServer: {
    contentBase: './dist', // 要服务的文件夹
    inline: true, // 实时监听
    // port: 8081, // 可以不指定，默认是在8080端口的
  }
})