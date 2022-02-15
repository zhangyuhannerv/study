import {
  ADD_COUNTER,
  ADD_TO_CART
} from './mutation-types'

export default {
  /*为了符合mutations的设计原则*/
  /*复杂的操作和异步操作一样放在actions里，并且调用不同的mutation来更改state的状态*/
  addCart(context, payload) {
    return new Promise((resolve, reject) => {
      let oldProduct = context.state.cartList.find(item => item.iid === payload.iid)
      if (oldProduct) {
        context.commit(ADD_COUNTER, oldProduct)
        resolve('当前商品数量+1')
      } else {
        payload.count = 1
        context.commit(ADD_TO_CART, payload)
        resolve('添加了新的商品')
      }
    })
  }
}
