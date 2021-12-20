<template>
  <div>
    <h2>我是hello vuex组件</h2>
    <h2>
      这是从父组件传过来的counter:
      {{ counter }}
    </h2>
    <h2>
      这是从vuex里取出来的counter:
      {{ $store.state.shareCounter }}
    </h2>
    <h2>
      这是从vuex的getters里取出来的shareCounter的平方
      {{ $store.getters.powSharedCounter }}
    </h2>

    获取年龄大于20岁的学生：
    <h2>
      计算属性（缺点：不支持多个组件，如果多个组件都要用，那么需要复制计算属性到别的组件中去，代码复用性低）：
      {{ more20stu }}
    </h2>
    <h2>
      getters（支持多个组件公用，并且同样有缓存，所以对于vuex管理的状态如果做变形处理，推荐使用getters）：
      {{ $store.getters.more20stu }}
    </h2>
    年龄大于20岁的学生的数量：
    <h2>
      {{ $store.getters.more20stuLength }}
    </h2>
    年龄大于15岁的学生的数量：
    <h2>
      {{ $store.getters.moreAgeStu(15) }}
    </h2>
  </div>
</template>

<script>
export default {
  name: "HelloVuex",
  // 这是通过父组件向子组件传递参数来达到共享状态的方式
  // 当组件之间没有父子关系的时候，如果仍需共享某种状态，此时就需要vuex了
  props: {
    counter: Number
  },
  computed: {
    // 计算属性，过滤出大于20岁的学生
    more20stu() {
      return this.$store.state.students.filter(e => e.age > 20)
    }
  }
}
</script>

<style scoped>

</style>
