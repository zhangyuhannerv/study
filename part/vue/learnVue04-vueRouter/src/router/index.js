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

const routes = [
  // 设置默认路径
  {
    path: '',// 首页路由(路径是缺省值)。这里写'/'或者直接写''都可以
    // component: Home,// 这样的话虽然会默认显示Home,但是浏览器的地址栏上是/#/而不是/home,所以不要这么做
    // 重定向：重新定义一个方向
    redirect: '/home'
  },
  {
    path: '/home',
    component: Home,
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
  },
  {
    // path: '/usr',// 这是最基本的配置方式
    path: '/usr/:usrId',// 这是动态路由的配置方式
    component: Usr,
  }
]

const router = new VueRouter({
  routes,
  mode: 'history',// 因为默认的vueRouter使用hash模式，所以地址栏上会带个#号，难看。修改为history模式
  linkActiveClass: 'active'// 全局修改处于活跃的router-link标签的'router-link-active'的class改为'active'
});

// 3.将router对象传入Vue实例中
export default router;
