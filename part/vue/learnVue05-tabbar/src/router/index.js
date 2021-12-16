import Vue from 'vue'
import VueRouter from "vue-router";

const Home = () => import('../views/home/Home')
const About = () => import('../views/about/About')
const Shopcar = () => import('../views/shopcar/Shopcar')
const Profile = () => import('../views/profile/Profile')

// 1.安装插件
Vue.use(VueRouter);

// 2.创建路由对象
const routes = [
  {
    path: '',
    redirect: '/home'
  },
  {
    path: '/home',
    component: Home
  },
  {
    path: '/about',
    component: About
  },
  {
    path: '/shopcar',
    component: Shopcar
  },
  {
    path: '/profile',
    component: Profile
  },
];
const router = new VueRouter({
  routes,
  mode: 'history'
})

// 3.导出router
export default router
