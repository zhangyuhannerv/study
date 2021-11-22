0. npm install 模块名@版本号 --save:将保存配置信息到pacjage.json的dependencies节点中。

   npm install 模块名@版本号 --save-dev:将保存配置信息到pacjage.json的devDependencies节点中。

   dependencies：运行时的依赖，发布后，即生产环境下还需要用的模块

   devDependencies：开发时的依赖。里面的模块是开发时用的，发布时用不到它。

1. 进入到D:\Work\Study\study\part\vue\learnVue02-webpack\01-webpack的起步文件夹

   执行以下命令

   ```she
   webpack .\src\main.js .\dist\bundle.js
   ```

   注意mathUtil不用管，打包时，webpack会自动处理分析模块之间的依赖 

   补充：mathUtil还依赖别的文件，也不用管，因为我们只需要打包的时候给定一个入口文件main.js/index.js

2. 不再输入上述繁琐的命令，直接执行webpack，就能把main.js打包成bundle.js

   执行

   ```shell
   webpack
   ```

   编译完成
   
   这个功能的实现详见webpack.config.js这个文件

3. 把webpack和npm run build(真实的打包命令)这两条命令对应起来

   真实需求：映射 webpack 的各种命令到 npm run... 

   解决办法：修改pacage.json的scripts属性

   加上这么个映射属性

   ```json
   "scripts": {
       "test": "echo \"Error: no test specified\" && exit 1",
       "build": "webpack"
   },
   ```

   加上这么个映射之后虽然效果一样。但是有点细微的区别

   **注意**

   直接执行webpack用的是全局的webpack命令

   但是执行npm run build会优先在本地找webpack。找不到才取全局找。真实的开发项目中都会有个本地的webpack包，因为项目实际需要的webpack和全局安装的webpack可能版本不一致。

4. 安装项目本地的webpack

   ```shell
   npm install webpack@3.6.0 --save-dev 
   ```

   webpack是开发时的依赖，它的主要作用就是打包。在项目真正运行时是不需要webpack的。所以需要加个--save-dev的后缀

   -g是node的全局安装后缀

   安装完后package.json多了个这个属性

   ```json
     "devDependencies": {
       "webpack": "^3.6.0"
     }
   ```

5. 扩展webpakc的能力（loader)

   基础的webpack打包命令不具备将sass/less转成css。es6/.ts/.jsx等文件或者语法转换成es5。此时需要给webpack扩展相应的 loader

   步骤1：通过npm安装需要的loader

   步骤2：在webpack.config.js的文件中的modules关键字下进行配置
   
   ------
   
   以css-loader为例：官网上有详细的安装使用教程（前看官网)。下面简写：
   
   注意学习版本安装css-loader的时候执行这个命令（指定css-loader的版本)，直接安装最新版本的css-loader是报错的。因为webpack学习时的版本是3.6.0，太老了。
   
   ```shell
   npm install --save-dev css-loader@0.28.11
   ```
   
   然后在webpack.config.js的导出对象里加上这么个属性
   
   ```json
   module: {
       rules: [
         {
           test: /\.css$/i,
           use: ["css-loader"],
         },
       ],
     },
   ```
   
   注意：只安装css-loader只是将css进行加载。要想将css进行解析和渲染还需要style-loader
   
   ```js
   npm install --save-dev style-loader@0.23.1
   ```
   
   配置（上面的稍微改下就行)：
   
   ```js
    use: ["style-loader", "css-loader"],
   ```
   
   
   
   ------
   
   ***核心思想**
   
   就是将css/json/less/sass作为一个模块打包到bundle.js里面
   
   依赖css需要在main.js里面加上
   
   ```js
   // 3.依赖css文件（将css作为module)
   require('./css/normal.css')
   ```

​		
