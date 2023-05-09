// GL版命名空间为BMapGL
// 按住鼠标右键，修改倾斜角和角度
var map = new BMapGL.Map("allmap");    // 创建Map实例
var point = new BMapGL.Point(116.404, 39.915);
map.centerAndZoom(point, 15);  // 初始化地图,设置中心点坐标和地图级别
map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放


function flyToBeijing() {
    map.reset()
}

function flyToJinan() {
    map.flyTo(new BMapGL.Point(117.05274833198338, 36.63647205417621), 15);
}




