import Vue from "vue";
import VueRouter from "vue-router/dist/vue-router.esm.browser";

const Home = () => import('views/home/Home')
const Category = () => import('views/category/Category')
const Profile = () => import('views/profile/Profile')
const Shopcar = () => import('views/shopcar/Shopcar')
const Detail = () => import('views/detail/Detail')

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
    path: '/category',
    component: Category
  },
  {
    path: '/shopcar',
    component: Shopcar
  },
  {
    path: '/profile',
    component: Profile
  },
  {
    path: '/detail/:id',
    component: Detail
  },
]

const router = new VueRouter({
  routes,
  mode: 'history'
})

export default router
