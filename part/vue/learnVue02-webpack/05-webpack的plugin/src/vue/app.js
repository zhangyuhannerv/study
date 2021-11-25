// 此时template里的h5代码没有和js分离
// 还要分离。最终效果就是App.vue
export default {
  template: `
    <div>
    <h2>{{ message }}</h2>
    <h2>{{ name }}</h2>
    <button @click="btnClick">按钮</button>
    </div>`,
  data() {
    return {
      message: 'hello webpack',
      name: '张三'
    }
  },
  methods: {
    btnClick() {
      console.log("111");
    }
  },
}
