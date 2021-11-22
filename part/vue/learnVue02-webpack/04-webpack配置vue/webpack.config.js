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
    filename: 'bundle.js',
    // 这个属性意味着只要是任何设计url的东西，都会在前面添加dist/(在这里主要解决背景图片的引用总是引用根目录下的图片的问题,加上他就会
    // 引用dist目录下打包后的图片)
    publicPath: 'dist/'// 在后期打包后把index.html移动到dist里去，这个属性要删除掉。目前不要删除，因为此时index.html还在外面
  },
  module: {
    rules: [
      // css的loader
      {
        test: /\.css$/i,// 做文件匹配的正则表达式
        // 注意：css-loader只负责将css文件进行加载，不负责解析，也不负责将css放到html里进行渲染
        // style-loader负责将样式添加到dom中负责生效
        // 补充：webpack使用多个loader时是从右向左读，所以先用css-loader后用style-loader是按照下面的顺序来写
        use: ["style-loader", "css-loader"],
      },
      // less的loader
      {
        test: /\.less$/i,
        loader: [
          // compiles Less to CSS
          'style-loader',
          'css-loader',
          'less-loader',
        ],
      },
      //  图片的loader(webpack5删除了该loader。不需要loader可以直接打包图片文件)
      {
        test: /\.(png|jpg|gif|jpeg)$/,
        use: [
          {
            loader: 'url-loader',
            options: {
              // 当加载的图片小于limit。会将图片编译成base64字符串形式
              // -----------------------------------------------
              // 当加载的图片大于limit时，不再使用url-loader而是使用file-loader来装载图片
              // 注意:file-loader不用配置，只需下载即可。但是如果index.html不在dist里面需要在上面配置个publicPath属性
              // file-loader补充：打包后的图片会重新名命，名字是32位的hash值，目的是为了防止图片名称重复
              limit: 20480,// 20kB（默认是8kb，开发时使用8即可。这里为了展示效果使用了20）
              // 但是，在真实的开发中。我们希望图片的名称有一定的规范(即带上原名称)，并统一放到dist/img下
              // 大于20kb的图片打包后的名命规范如下(dist/img/原名称.前8位hash.文件类型)
              name: 'img/[name].[hash:8].[ext]'
            }
          }
        ]
      },
      //  es6转es5
      {
        test: /\.m?js$/,
        // include:包含
        // exclude:排除
        exclude: /(node_modules|bower_components)/,// 排除某些文件夹，不要把他们里的js代码从es6转成es5
        use: {
          loader: 'babel-loader',
          options: {
            presets: ['es2015']
          }
        }
      },
      // .vue文件的loader。这里高版本的vue-loader需要和插件配合使用。所以特地降低了一下版本(从15降到了13)
      {
        test: /\.vue$/i,
        use: ['vue-loader']
      }
    ],
  },
  resolve: {
    // alis：别名
    alias: {
      // 把项目中引入的vue实例指向到具体的文件夹。不会再去找默认的vue实例了
      // 默认应该是引入vue.runtime.js里的vue实例
      // 此时就相当于开启了runtime-compiler模式了
      'vue$': 'vue/dist/vue.esm.js'
    },
    // 这里可以配置在导入的时候文件的路径最后哪些后缀可以不用写
    extensions: ['.js', '.vue', '.css'],
  }
}
