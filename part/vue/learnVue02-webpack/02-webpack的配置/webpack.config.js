const path = require('path')// 导入node的path模块

// 但是这个path包还没有。所以要在当前项目下局部安装path包
// 0.先在项目的根目录下执行 node init ,
// 1.输入个包的名字（默认是项目根目录的名字，不好，有中文，所以需要自己起个名字（meetwebpack））
// 2.之后看名称自己输入就行（一路默认）
// 3.entry point:自己随便写就行(index.js),这个东西无论如何都是用不上的
// 4.一路默认
// 5.最后生成了个package.json文件（任何项目只要需要node环境，基本上都要有package.json这个文件，建议在最开始就弄上）
// 6.如果package.json里面还依赖一些东西（额外的包，不是node自带的包等等)，还需要执行npm.install命令(这个项目(02-webpack的0配置)啥都没有，执不执行都无所谓)
// 7.生成了个package-lock.json(用不上)
// 8.over


module.exports = {
  entry: './src/main.js',// 入口
  output: {
    // path: './dist',// 这里写相对路径会报错
    path: path.resolve(__dirname, 'dist'),// 这里需要绝对路径，所以要动态获取，需要node的path模块,__dirname就是webpack.config.js这个文件所在文件夹的绝对路径
    filename: 'bundle.js'
  }
}
