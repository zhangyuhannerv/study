<template>
  <div id="container"></div>
</template>


<script>
export default {
  name: 'map'
}

/*import AMapLoader from '@amap/amap-jsapi-loader';
import {shallowRef} from '@vue/reactivity';

let Map;


function drawLineTest() {
  // 初始化地图
  var map = new window.AMap.Map('map-container', {
    viewMode: "3D", //是否为3D地图模式
    zoom: 10, //初始化地图级别
    center: [116.397428, 39.90923], //初始化地图中心点位置
  })

  // 实例化某以城市公交地铁线路查询类（以广州为例）
  var linesearch = new window.AMap.LineSearch({
    pageIndex: 1, // 第一页的线路
    city: '北京',
    pageSize: 1, // 每一页的线路条数
    extensions: 'all' // 所有线路类型
  })

  // 开始搜索
  var name = '大兴机场线'
  linesearch.search(name, (status, result) => {
    if (status === 'complete' && result.info === 'OK') {
      console.log(result)
      // 查询成功调用
      lineSearch_Callback(result)
    } else {
      console.log('查询出错')
    }
  })

  /!* 地铁路线查询服务返回数据解析概况 *!/
  function lineSearch_Callback(data) {
    var lineArr = data.lineInfo;
    var lineNum = data.lineInfo.length;
    if (lineNum == 0) {
      console.log(lineNum)
    } else {
      for (var i = 0; i < lineNum; i++) {
        var pathArr = lineArr[i].path;
        if (i == 0) // 只绘制一条线路，就是需要高亮的地铁线路
          drawbusLine(pathArr);
      }
    }
  }

  /!* 绘制路线 *!/
  function drawbusLine(BusArr) {
    let busPolyline = new window.AMap.Polyline({
      map: map,
      path: BusArr,
      strokeColor: 'red', // 线颜色
      strokeOpacity: 0.8, // 线透明度
      isOutline: true,
      outlineColor: 'white',
      strokeWeight: 10 // 线宽
    })
  }
}


export default {
  name: "Map",
  setup() {
    const map = shallowRef(null);
    return {
      map,
    }
  },
  data() {
    return {
      cityName: '北京',
      lineName: '大兴机场线',
      lineSearch: null,
      drawLine: null,

      lineSearchResult: null,
      pointArr: null,
    }
  },
  methods: {
    analysisSearchResult() {
      let lineArr = this.lineSearchResult.lineInfo;
      let lineNum = lineArr.length;
      if (lineNum === 0) {
        this.pointArr = []
      } else {
        // 只画搜索到第一条线路
        this.pointArr = lineArr[0].path
      }
    },

    searchLine() {
      // 开始搜索
      this.linesearch.search(this.lineName, (status, result) => {
        if (status === 'complete' && result.info === 'OK') {
          this.lineSearchResult = result
          // 分析数据
          this.analysisSearchResult()
          // 画线
          this.drawLine()
        } else {
          alert('查询出错')
        }
      })
    },

    initMap() {
      AMapLoader.load({
        // a45b8935299e65fa1631dbef2d05d6cf 大兴的key
        // 1fae17950473188947c483b4606878ae 我的key
        key: "1fae17950473188947c483b4606878ae",             // 申请好的Web端开发者Key，首次调用 load 时必填
        version: "2.0",      // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
        plugins: [
          'AMap.Scale',
          'AMap.ToolBar',
          'AMap.LineSearch',
        ],       // 需要使用的的插件列表，如比例尺'AMap.Scale'等
      }).then((AMap) => {
        Map = AMap
        // 初始化地图
        this.map = new AMap.Map("container", {  //设置地图容器id
          viewMode: "3D",    //是否为3D地图模式
          zoom: 10,           //初始化地图级别
          center: [116.397428, 39.90923], //初始化地图中心点位置
        });


        // 添加标尺
        let scale = new AMap.Scale();
        this.map.addControl(scale);

        // 添加放大缩小
        let tool = new AMap.ToolBar();
        this.map.addControl(tool);

        // 初始化线路查询方法
        // this.linesearch = new AMap.LineSearch({
        //   pageIndex: 1, // 第一页的线路
        //   city: this.cityName,
        //   pageSize: 1, // 每一页的线路条数
        //   extensions: 'all' // 所有线路类型
        // })
        //
        // // 初始化画线的方法
        // this.drawLine = () => {
        //   let busPolyline = new AMap.Polyline({
        //     map: this.map,
        //     path: this.pointArr,
        //     strokeColor: 'red', // 线颜色
        //     strokeOpacity: 0.8, // 线透明度
        //     isOutline: true,
        //     outlineColor: 'white',
        //     strokeWeight: 10 // 线宽
        //   })
        // }

        // this.searchLine()

      }).catch(e => {
        console.log(e);
      })
    },
  },
  mounted() {
    //DOM初始化完成进行地图初始化
    this.initMap();
  }
}*/
</script>

<style scoped>
#container {
  padding: 0;
  margin: 0;
  width: 100%;
  height: 800px;
}
</style>
