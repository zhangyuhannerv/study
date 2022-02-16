<template>
  <div class="toast" v-show="isShow">
    <div>{{ message }}</div>
  </div>
</template>

<script>
export default {
  name: "Toast",
  // props: {
  //   message: {
  //     type: String,
  //     default: ''
  //   },
  //   show: {
  //     type: Boolean,
  //     default: false
  //   }
  // },
  data() {
    return {
      message: '',
      isShow: false,
    }
  },
  methods: {
    /**
     *
     * @param message 信息
     * @param duration 持续时间，默认1.5s
     */
    show(message = '默认文字', duration = 1500) {
      this.isShow = true;
      this.message = message
      setTimeout(() => {
        this.isShow = false
        this.message = ''
      }, duration)
    }
  }
}
</script>

<style scoped>
.toast {
  z-index: 999;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 8px 10px;

  color: #fff;
  background-color: rgba(0, 0, 0, .75);
}
</style>
