// 在这个index.js里面配置所有的路由信息

// 导入vueRouter
import VueRouter from 'vue-router'
// 导入Vue
import Vue from 'vue';

// 1.通过vue.use(插件)来安装插件,任何插件（不仅包括vue-router)。
Vue.use(VueRouter);

// 2.创建路由对象
// 在routes配置组件和路径的关系
// import Home from '../components/Home';
// import About from '../components/About';
// import Usr from "../components/Usr";

// 代码优化，路由懒加载：目的是为了能够让不同路由对应的不同的组件在打包的时候打包到不同的js里面去。在需要该组件的时候，才去加载对应的js。而不是在项目的首页就
// 去加载整个大的业务js。这么写完之后执行打包命令可以看到dist的js里面除了3个基本的。剩下的就是有几个路由，就有几个组件js。
const Home = () => import('../components/Home')
const About = () => import('../components/About')
const Usr = () => import('../components/Usr')
const HomeNews = () => import('../components/HomeNews')
const HomeMessage = () => import('../components/HomeMessage')
const Profile = () => import('../components/Profile')

const routes = [
  // 设置默认路径
  {
    path: '', // 首页路由(路径是缺省值)。这里写'/'或者直接写''都可以
    // component: Home,// 这样的话虽然会默认显示Home,但是浏览器的地址栏上是/#/而不是/home,所以不要这么做
    // 重定向：重新定义一个方向
    redirect: '/home'
  },
  {
    path: '/home',
    component: Home,
    meta: { // 当前路由的元数据，用来保存当前路由对象的一些信息
      title: '首页'
    },
    // 嵌套路由
    // 注意：子路由的path前面不需要加'/'
    children: [
      // 让进入到home组件时默认显示news组件。相当于给了个路径为'/home/'的默认首页
      {
        path: '',
        redirect: 'news'
      },
      {
        path: 'news',
        component: HomeNews
      },
      {
        path: 'message',
        component: HomeMessage
      }
    ]
  },
  {
    path: '/about',
    component: About,
    meta: { // 当前路由的元数据，用来保存当前路由对象的一些信息
      title: '关于'
    },
  },
  {
    // path: '/usr',// 这是最基本的配置方式
    path: '/usr/:usrId', // 这是动态路由的配置方式
    component: Usr,
    meta: { // 当前路由的元数据，用来保存当前路由对象的一些信息
      title: '用户'
    },
  },
  {
    path: '/profile',
    component: Profile,
    meta: { // 当前路由的元数据(描述数据的数据)，用来保存当前路由对象的一些信息
      title: '配置'
    },
    beforeEnter: (to, from, next) => {
      //  这个beforeEnter就是路由独享的守卫
      next();
      console.log('进入配置文件')
    }
  }

]

const router = new VueRouter({
  routes,
  mode: 'history', // 因为默认的vueRouter使用hash模式，所以地址栏上会带个#号，难看。修改为history模式
  linkActiveClass: 'active' // 全局修改处于活跃的router-link标签的'router-link-active'的class改为'active'
});

// 这里配置全局导航守卫
// beforeEach()称之为前置钩子。是路由跳转之前的回调
router.beforeEach((to, from, next) => {
  next(); // 这里必须要调用next(),不调next()组件的跳转就失效了
  // 从from路由对象跳转到to路由对象
  // document.title = to.meta.title;
  // 当有嵌套路由的时候，会定位到子路由。而子路由没有meta.title
  // 此时网页的标题是undefined
  // 此时要这么改下代码
  document.title = to.matched[0].meta.title; // 意思是找到匹配的路由的根节点的meta.title。
  console.log('路由跳转之前')
})

// 还有个后置钩子afterEach()。是路由跳转完的回调。此时不用调用next()函数
router.afterEach((to, from) => {
  // document.title = to.matched[0].meta.title;// 这里也能实现改变页面的标题
  console.log('路由跳转之后')
})

// beforeEach()和afterEach()称之为全局守卫，是整个router的属性。只要有页面发生跳转，都能监听的到
// 还有路由独享的守卫和组件内的守卫
// 路由独享守卫是只有进入某个路由里面才能进入钩子函数
// 组件内的守卫是只有进入某个组件内才能进入钩子函数
// 路由独享守卫和组件内的守卫用到的时候去官方文档上自己学习

// 3.将router对象传入Vue实例中
export default router;
