export default {
  state: {
    name: 'zhangsan'
  },
  mutations: {
    // 注意：模块里的mutation的名字不要和store里的mutation的名字重复
    updateAName(state, payload) {
      console.log(payload)
      state.name = 'lisi'
    }
  },
  getters: {
    fullName(state) {
      return state.name + '111'
    },
    fullName2(state, getters) {
      return getters.fullName + '222'
    },
    fullName3(state, getters, rootState) {
      // 访问store里的getters
      return getters.fullName2 + rootState.shareCounter
    }
  },
  actions: {
    aUpdateName(context) {
      console.log(context);// 打印发现里面还有getters，rootGetters,state,rootState,见名知意，如果想取别的值，直接取即可
      // 注意：此时的context，它就不再是store对象了，它具体是什么老师没说
      // 但是此时的context也有个commit，它只能commit他所在模块里的mutation，不能commit外部store的mutation
      setTimeout(() => {
        context.commit('updateAName', 'wangwu')
      }, 1000)
    }
  },
}
