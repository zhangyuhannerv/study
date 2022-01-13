import {request} from "network/request";

export function getDetail(iid) {
  return request({
    url: '/detail',
    params: {
      iid
    }
  })
}

// 格式（整合）杂乱的数据为一个对象方便组件调用
export class Goods {
  constructor(itemInfo, columns, services) {
    this.title = itemInfo.title
    this.desc = itemInfo.desc
    this.newPrice = itemInfo.price
    this.oldPrice = itemInfo.oldPrice
    this.discount = itemInfo.discountDesc
    this.columns = columns
    this.services = services
    this.realPrice = itemInfo.lowNowPrice
  }
}

// 店铺数据
export class Shop {
  constructor(shopInfo) {
    this.logo = shopInfo.shopLogo;
    this.name = shopInfo.name;
    this.fans = shopInfo.cFans;
    this.sells = shopInfo.cSells;
    this.score = shopInfo.score;
    this.goodsCount = shopInfo.cGoods;
  }
}


// es6的类的简单使用
// class person {
//   constructor(name, age) {
//     this.name = name;
//     this.age = age;
//   }
// }
//
// const p = new person('张三', 18)
//
// console.log(p)
