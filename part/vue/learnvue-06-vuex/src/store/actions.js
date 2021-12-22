import * as types from "./mutation-types";

export default {
  // context:上下文，在这里要理解为store对象
  updateName(context, payload) {
    console.log(payload)
    // 异步的操作
    setTimeout(() => {
      // 异步的回调函数，通过commit修改state
      context.commit(types.UPDATENAME)
    }, 1000)
  },

  // 异步修改信息之后，能成功回调，告诉外面，改的成功与否，改的结果是啥
  updateName1(context, payload) {
    // action返回一个promise对象，在外部dispatch当前action时的时候，写then或者catch
    return new Promise((resolve, reject) => {
      // 异步的操作
      setTimeout(() => {
        // 异步的回调函数，通过commit修改state
        context.commit(types.UPDATENAME)
        console.log(payload)
        // 成功了
        resolve('成功了')
        // 失败了
        // reject('失败了')
      }, 1000)
    })

  }
}
