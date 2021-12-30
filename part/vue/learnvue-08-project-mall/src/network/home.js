import {request} from "network/request";

// 本页面写首页所有的请求
export function getHomeMultiData() {
  return request({
    url: '/home/multidata'
  })
}
