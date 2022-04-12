<template>
  <div id="container"></div>
</template>

<script>
import AMapLoader from '@amap/amap-jsapi-loader';
import {shallowRef} from '@vue/reactivity'

export default {
  name: "Map",
  setup() {
    const map = shallowRef(null);
    return {
      map,
    }
  },
  methods: {
    initMap() {
      AMapLoader.load({
        key: "1fae17950473188947c483b4606878ae",             // 申请好的Web端开发者Key，首次调用 load 时必填
        version: "2.0",      // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
        plugins: [
          'AMap.Scale',
        ],       // 需要使用的的插件列表，如比例尺'AMap.Scale'等
      }).then((AMap) => {
        this.map = new AMap.Map("container", {  //设置地图容器id
          viewMode: "3D",    //是否为3D地图模式
          zoom: 10,           //初始化地图级别
          center: [116.397428, 39.90923], //初始化地图中心点位置
        });

        // const scale = new AMapLoader.Scale({
        //   position: {
        //     top: '110px',
        //     right: '40px'
        //   }
        // })
        //
        // this.map.addControl(scale)

      }).catch(e => {
        console.log(e);
      })
    },
  },
  mounted() {
    //DOM初始化完成进行地图初始化
    this.initMap();
  }
}
</script>

<style scoped>
#container {
  padding: 0;
  margin: 0;
  width: 100%;
  height: 800px;
}
</style>
