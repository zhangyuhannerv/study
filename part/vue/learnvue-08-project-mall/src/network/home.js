import {request} from "network/request";

// 本页面写首页所有的请求
export function getHomeMultiData() {
  return request({
    url: '/home/multidata'
  })
}

export function getHomeGoods(type, page) {
  return request({
    url: '/home/data',
    params: {
      type,
      page
    }
  })
}
