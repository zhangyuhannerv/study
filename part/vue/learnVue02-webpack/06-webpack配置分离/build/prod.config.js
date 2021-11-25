// 生产时的配置
const UglifyJsPlugin = require('uglifyjs-webpack-plugin') // 导入压缩js的插件
const webpackMerge = require('webpack-merge') // 合并webpack配置的node包
const baseConfig = require('./base.config')

module.exports = webpackMerge(baseConfig, {
  plugins: [
    // uglifyJsPlugin:对打包的js文件进行压缩。（去除空格和缩进，替换变量名字为简单符号)
    new UglifyJsPlugin(), // 此时上面的声明插件就没什么用了，因为会将注释都删除掉
  ],
})