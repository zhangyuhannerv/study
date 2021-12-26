<template>
  <h2>{{ categories }}</h2>
</template>

<script>
// 这样每次都引入默认的axios。那么对axios的依赖太高，如果将来需要更换网络请求的框架。那么所有的页面的关于axios的代码都需要更改
// 由此引申。项目中类似axios这种第三方库，需要自己做一层封装，然后需要用到的axios的地方都是用自己写的代码。
// 这样如果更换axios，那么只需更改封装代码即可
import axios from "axios";
import {
  instance1
  , instance2
  , instance3
} from "../network/request";

export default {
  name: "HelloWorld",
  data() {
    return {
      categories: '',
    }
  },
  created() {
    axios({
      url: 'http://123.207.32.32:8000/category',
    }).then(res => {
      this.categories = res;
    })

    instance1({
      url: '/home/multidata'
    }, res => {
      console.log('----自己封装的实例1start----')
      console.log(res)
      console.log('----自己封装的实例1end----')
    }, error => {
      console.log(error)
    })

    instance2({
      baseConfig: {
        url: '/home/multidata',
      },
      success(res) {
        console.log('----自己封装的实例2start----')
        console.log(res)
        console.log('----自己封装的实例2end----')
      },
      failure(err) {
        console.log(err)
      }
    })

    instance3({
      url: '/home/multidata'
    }).then(res => {
      console.log('----自己封装的实例3start----')
      console.log(res)
      console.log('----自己封装的实例3end----')
    }).catch(err => {
      console.error(err)
    })
  }
}
</script>

<style scoped>

</style>
