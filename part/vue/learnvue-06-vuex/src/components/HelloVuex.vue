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
    <hr/>
    展示store里info对象的数据是否时响应式的
    <h2>{{ $store.state.info }}</h2>
    <!-- 修改信息后，发现界面展示信息的部分也发生变化 -->
    <!-- 这是因为info是在store初始创建的时候就放入到state里了，此时info已经加入到vue的响应式系统了。该系统会监听 -->
    <!-- info的属性的变化，一旦info的某个属性发生改变，会通知所有界面中用到该属性的地方，让界面发生刷新 -->
    <button @click="updateInfo">修改信息</button>
    <!-- 修改信息1给info对象新增了个属性'address',此时发现虽然info的确有这个新增的属性，但是界面不会发生变化 -->
    <!-- 因为这个新增的address属性没有加入到响应式系统中，它没有对应的监听 -->
    <!-- 因此这是一种错误的给对象添加属性的方式 -->
    <button @click="updateInfo1">错误修改信息1</button>
    <!-- 正确的给对象新增属性，Vue.set() -->
    <!-- 此时新增的属性也会增加到响应式系统里面 -->
    <button @click="updateInfo2">正确修改信息2</button>

    <!-- 演示异步操作，devtools无法有效的跟踪提交 -->
    <button @click="updateInfo3">错误异步修改信息</button>

    <button @click="updateName">正确异步修改信息</button>

    <button @click="updateName1">正确异步修改信息并返回结果</button>
    <hr>
    演示modules里内容：
    <!--    a虽然再modules里，是vuex的一个模块，但是最终是放在了vuex的state里,这一点从devtools的vuex的基本状态里就可一看出来-->
    <!--    把模块a的state重命名为a，放在了vuex的state下-->
    <h2>模块a：{{ $store.state.a }}</h2>
    <h2>模块a中的name：{{ $store.state.a.name }}</h2>
    <button @click="updateModuleAName">修改模块a中的名字(测试模块里的mutation)</button>
    <!--    模块中的getters也是直接使用即可-->
    <!--    和mutation一样，vuex并不关心你的getters是定义在哪里的。是在store里还是再modules里，都是直接使用-->
    <h2>模块中的getter：{{ $store.getters.fullName }}</h2>
    <h2>模块中的getter：{{ $store.getters.fullName2 }}</h2>
    <h2>模块中的getter：{{ $store.getters.fullName3 }}</h2>
    <!--    模块内的action-->
    <button @click="asyncUpdateName">调用模块内部的action来更改state</button>
  </div>
</template>

<script>
// 引入类型常量文件
import * as types from "../store/mutation-types";

export default {
  name: "HelloVuex",
  // 这是通过父组件向子组件传递参数来达到共享状态的方式
  // 当组件之间没有父子关系的时候，如果仍需共享某种状态，此时就需要vuex了
  props: {
    counter: Number,
  },
  computed: {
    // 计算属性，过滤出大于20岁的学生
    more20stu() {
      return this.$store.state.students.filter((e) => e.age > 20);
    },
  },
  methods: {
    updateInfo() {
      this.$store.commit("updateInfo");
    },
    updateInfo1() {
      this.$store.commit("updateInfo1");
    },
    updateInfo2() {
      // this.$store.commit("updateInfo2");
      this.$store.commit(types.UPDATEINFO2);
    },

    updateInfo3() {
      this.$store.commit(types.UPDATEINFO3);
    },

    updateName() {
      // 感觉也可以像commit一个对象那样dispatch一个对象,但是我没有试过
      this.$store.dispatch("updateName", "action传过去的参数");
    },

    updateName1() {
      this.$store
        .dispatch("updateName1", "你好吗")
        .then((e) => {
          console.log(e);
        })
        .catch((e) => {
          console.log(e);
        });
    },

    updateModuleAName() {
      // 这个提交会优先找store里的mutation，找不到再找模块里的mutation
      this.$store.commit('updateAName', 'dd')
    },

    asyncUpdateName() {
      this.$store.dispatch('aUpdateName')
    }
  },
};
</script>

<style scoped>
</style>
