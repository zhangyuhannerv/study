<template>
  <input type="text" v-model="lineName">
  <button @click="changeLine">查询</button>
  <button @click="clearMark">删除线</button>
  <div id="container"></div>
</template>

<script>
import {shallowRef} from '@vue/reactivity';

export default {
  name: 'maps',
  setup() {
    const map = shallowRef(null);
    return {
      map,
    }
  },
  data() {
    return {
      disCountry: null,
      mask: [],

      lineName: '',
      cityName: '北京',
      lineSearch: null,
      drawLine: null,

      linePointArr: null,
    }
  },
  mounted() {
    // this.getYanmo()
    // this.getLocation()

    this.initMap()
  },
  methods: {
    clearMark() {
      this.map.clearMap();
    },

    changeLine() {
      this.searchLine(this.lineName)
    },

    analysisSearchResult(result) {
      // 只画搜索到第一条线路
      this.linePointArr = result.lineInfo[0].path
    },

    searchLine(lineName) {
      // 清空之前的标记
      this.clearMark()

      // 开始搜索
      this.linesearch.search(lineName, (status, result) => {
        if (status === 'complete' && result.info === 'OK') {
          this.lineSearchResult = result
          // 分析数据
          this.analysisSearchResult(result)
          // 画线
          this.drawLine()
        } else if (status === 'no_data') {
          alert('无线路数据')
        } else {
          alert('查询出错')
        }
      })
    },

    initMap() {
      const that = this
      that.map = new AMap.Map("container", {  //设置地图容器id
        viewMode: "3D",    //是否为3D地图模式
        zoom: 10,           //初始化地图级别
        center: [116.397428, 39.90923], //初始化地图中心点位置
      });


      // 添加插件
      // 添加标尺
      let scale = new AMap.Scale();
      that.map.addControl(scale);

      // 添加放大缩小
      let tool = new AMap.ToolBar();
      that.map.addControl(tool);

      // 初始化查询方法
      that.linesearch = new AMap.LineSearch({
        pageIndex: 1, // 第一页的线路
        city: that.cityName,
        pageSize: 1, // 每一页的线路条数
        extensions: 'all' // 所有线路类型
      })

      // 初始化画线的方法
      that.drawLine = () => {
        let subwayLine = new AMap.Polyline({
          path: that.linePointArr,
          strokeColor: 'red', // 线颜色
          strokeOpacity: 0.8, // 线透明度
          isOutline: true,
          outlineColor: 'white',
          strokeWeight: 10 // 线宽
        })

        that.map.add(subwayLine)
      }

      // 画线
      this.searchLine('大兴机场线')
    },


    // 掩模
    getYanmo() {
      AMap.plugin("AMap.DistrictSearch", () => {
        var opts = {
          subdistrict: 0,   //返回下一级行政区
          extensions: 'all',  //返回行政区边界坐标组等具体信息
        };
        var district = new AMap.DistrictSearch(opts);
        district.search('中国', (status, result) => {
          var bounds = result.districtList[0].boundaries;
          // console.log('bounds', bounds);
          this.$nextTick(() => {
            if (bounds.length > 0) {
              for (var i = 0; i < bounds.length; i += 1) {
                this.mask.push([bounds[i]])
              }
            }
          })
          console.log('this.mask', this.mask);
          AMap.plugin("AMap.DistrictLayer", () => {
            this.disCountry = new AMap.DistrictLayer.World({
              zIndex: 1,
              rejectMapMask: true
            })
          })
          this.map = new AMap.Map('container', {
            mask: this.mask,
            center: [120.16263, 33.348176],

            viewMode: '3D',
            labelzIndex: 130,
            zoom: 3,
            cursor: 'pointer',
            layers: [
              new AMap.TileLayer.RoadNet({
                zIndex: 7
              }),
              this.disCountry,
              new AMap.TileLayer.Satellite()
            ]
          });
        })
      })
    },
    //定位
    getLocation() {
      AMap.plugin("AMap.Geolocation", function () {
        var geolocation = new AMap.Geolocation({
          // 是否使用高精度定位，默认：true
          enableHighAccuracy: true,
          // 设置定位超时时间，默认：无穷大
          timeout: 2000
        });

        geolocation.getCurrentPosition();
        AMap.event.addListener(geolocation, "complete", onComplete);
        AMap.event.addListener(geolocation, "error", onError);

        function onComplete(data) {
          // data是具体的定位信息  精准定位
          console.log('定位', data);

        }

        function onError(data) {
          // 定位出错    非精准定位
          // console.log(data);
        }
      });

      /* 获取天气 */
      AMap.plugin('AMap.Weather', function () {
        //创建天气查询实例
        var weather = new AMap.Weather();

        //执行实时天气信息查询
        weather.getLive('南京', function (err, data) {
          // console.log('err', err);
          console.log('天气', data);
        });
      });

      /* 公交站点查询 */
      AMap.plugin(["AMap.StationSearch"], function () {
        //实例化公交站点查询类
        var station = new AMap.StationSearch({
          pageIndex: 1, //页码，默认值为1
          pageSize: 10, //单页显示结果条数，默认值为20，最大值为50
          city: '010' //限定查询城市，可以是城市名（中文/中文全拼）、城市编码，默认值为『全国』
        });

        //执行关键字查询
        station.search('南京南站', function (status, result) {
          //打印状态信息status和结果信息result
          //status：complete 表示查询成功，no_data 为查询无结果，error 代表查询错误。
          console.log('公交信息', result);
        });
      });


      /* 查询公交线路 */
      AMap.plugin(["AMap.LineSearch"], function () {
        //实例化公交线路查询类
        var linesearch = new AMap.LineSearch({
          pageIndex: 1, //页码，默认值为1
          pageSize: 1, //单页显示结果条数，默认值为20，最大值为50
          city: "南京", //限定查询城市，可以是城市名（中文/中文全拼）、城市编码，默认值为『全国』
          extensions: "all" //是否返回公交线路详细信息，默认值为『base』
        });

        //执行公交路线关键字查询
        linesearch.search('13路', function (status, result) {
          //打印状态信息status和结果信息result
          console.log('公交线路', result);
        });
      });

      /* 根据坐标查询地址 */
      AMap.plugin('AMap.Geocoder', function () {
        var geocoder = new AMap.Geocoder({
          // city 指定进行编码查询的城市，支持传入城市名、adcode 和 citycode
          city: '010'
        })

        var lnglat = [118.856371, 32.005985]

        geocoder.getAddress(lnglat, function (status, result) {
          if (status === 'complete' && result.info === 'OK') {
            // result为对应的地理位置详细信息
            console.log('坐标查询的地址', result);
          }
        })
      })

      /* 行政区 */
      AMap.plugin('AMap.DistrictSearch', function () {
        var districtSearch = new AMap.DistrictSearch({
          // 关键字对应的行政区级别，country表示国家
          level: 'country',
          //  显示下级行政区级数，1表示返回下一级行政区
          subdistrict: 1
        })

        // 搜索所有省/直辖市信息
        districtSearch.search('中国', function (status, result) {
          // 查询成功时，result即为对应的行政区信息
          console.log('行政区', result);
        })
      })
    },

  }
}
</script>

<style scoped>
#container {
  padding: 0;
  margin: 0;
  width: 100%;
  height: 900px;
}
</style>
