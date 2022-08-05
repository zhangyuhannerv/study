import Vue from 'vue'
import Router from 'vue-router'
import { normalizeURL } from '@nuxt/ufo'
import { interopDefault } from './utils'
import scrollBehavior from './router.scrollBehavior.js'

const _aec11288 = () => interopDefault(import('../pages/about.vue' /* webpackChunkName: "pages/about" */))
const _a61ddeda = () => interopDefault(import('../pages/lend/index.vue' /* webpackChunkName: "pages/lend/index" */))
const _72f65a28 = () => interopDefault(import('../pages/user.vue' /* webpackChunkName: "pages/user" */))
const _efab0f22 = () => interopDefault(import('../pages/user/index.vue' /* webpackChunkName: "pages/user/index" */))
const _6c1b793a = () => interopDefault(import('../pages/user/user1.vue' /* webpackChunkName: "pages/user/user1" */))
const _6bff4a38 = () => interopDefault(import('../pages/user/user2.vue' /* webpackChunkName: "pages/user/user2" */))
const _24a01bbb = () => interopDefault(import('../pages/lend/_id.vue' /* webpackChunkName: "pages/lend/_id" */))
const _6f515cfe = () => interopDefault(import('../pages/index.vue' /* webpackChunkName: "pages/index" */))

// TODO: remove in Nuxt 3
const emptyFn = () => {}
const originalPush = Router.prototype.push
Router.prototype.push = function push (location, onComplete = emptyFn, onAbort) {
  return originalPush.call(this, location, onComplete, onAbort)
}

Vue.use(Router)

export const routerOptions = {
  mode: 'history',
  base: '/',
  linkActiveClass: 'nuxt-link-active',
  linkExactActiveClass: 'nuxt-link-exact-active',
  scrollBehavior,

  routes: [{
    path: "/about",
    component: _aec11288,
    name: "about"
  }, {
    path: "/lend",
    component: _a61ddeda,
    name: "lend"
  }, {
    path: "/user",
    component: _72f65a28,
    children: [{
      path: "",
      component: _efab0f22,
      name: "user"
    }, {
      path: "user1",
      component: _6c1b793a,
      name: "user-user1"
    }, {
      path: "user2",
      component: _6bff4a38,
      name: "user-user2"
    }]
  }, {
    path: "/lend/:id",
    component: _24a01bbb,
    name: "lend-id"
  }, {
    path: "/",
    component: _6f515cfe,
    name: "index"
  }],

  fallback: false
}

function decodeObj(obj) {
  for (const key in obj) {
    if (typeof obj[key] === 'string') {
      obj[key] = decodeURIComponent(obj[key])
    }
  }
}

export function createRouter () {
  const router = new Router(routerOptions)

  const resolve = router.resolve.bind(router)
  router.resolve = (to, current, append) => {
    if (typeof to === 'string') {
      to = normalizeURL(to)
    }
    const r = resolve(to, current, append)
    if (r && r.resolved && r.resolved.query) {
      decodeObj(r.resolved.query)
    }
    return r
  }

  return router
}
