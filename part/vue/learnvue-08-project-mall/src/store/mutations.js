import {
  ADD_COUNTER,
  ADD_TO_CART
} from './mutation-types'

export default {
  // addCart移动到actions里了
  /*addCart(state, payload) {
    /!*let oldProduct = null
    for (let item of state.cartList) {
      if (item.iid === payload.iid) {
        oldProduct = item
      }
    }*!/

    let oldProduct = state.cartList.find(item => item.iid === payload.iid)
    if (oldProduct) {
      oldProduct.count += 1
    } else {
      payload.count = 1
      state.cartList.push(payload)
    }

  }*/

  /**
   * 购物车旧商品数量加1
   * @param state
   * @param payload
   * @constructor
   */
  [ADD_COUNTER](state, payload) {
    payload.count++
  },

  /**
   * 新的商品添加到购物车
   * @param state
   * @param payload
   */
  [ADD_TO_CART](state, payload) {
    payload.checked = true// 默认添加到购物车里的商品是选中的
    state.cartList.push(payload)
  }
}
