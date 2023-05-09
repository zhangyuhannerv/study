/**
 * 画覆盖物
 * @type {BMapGL.Marker}
 */
let markerPointArr = []
let centerPoint = new BMapGL.Point(116.404, 39.915)
markerPointArr.push(centerPoint)
var marker = new BMapGL.Marker(centerPoint); // 创建点

let point1 = new BMapGL.Point(116.399, 39.910);
let point2 = new BMapGL.Point(116.405, 39.920);
let point3 = new BMapGL.Point(116.425, 39.900);
markerPointArr.push(point1, point2, point3)
var polyline = new BMapGL.Polyline([
    point1, point2, point3
], {strokeColor: "blue", strokeWeight: 2, strokeOpacity: 0.5});   //创建折线

var circle = new BMapGL.Circle(point, 500, {strokeColor: "blue", strokeWeight: 2, strokeOpacity: 0.5}); //创建圆

let point4 = new BMapGL.Point(116.387112, 39.920977)
let point5 = new BMapGL.Point(116.385243, 39.913063)
let point6 = new BMapGL.Point(116.394226, 39.917988)
let point7 = new BMapGL.Point(116.401772, 39.921364)
let point8 = new BMapGL.Point(116.41248, 39.927893)
markerPointArr.push(point4, point5, point6, point7, point8)
var polygon = new BMapGL.Polygon([
    point4,
    point5,
    point6,
    point7,
    point8,
], {strokeColor: "blue", strokeWeight: 2, strokeOpacity: 0.5});  //创建多边形

var pStart = new BMapGL.Point(116.392214, 39.918985);
var pEnd = new BMapGL.Point(116.41478, 39.911901);
var rectangle = new BMapGL.Polygon([
    new BMapGL.Point(pStart.lng, pStart.lat),
    new BMapGL.Point(pEnd.lng, pStart.lat),
    new BMapGL.Point(pEnd.lng, pEnd.lat),
    new BMapGL.Point(pStart.lng, pEnd.lat)
], {strokeColor: "blue", strokeWeight: 2, strokeOpacity: 0.5});  //创建矩形

//添加覆盖物
function add_overlay() {
    remove_overlay()
    map.addOverlay(marker);            //增加点
    map.addOverlay(polyline);          //增加折线
    map.addOverlay(circle);            //增加圆
    map.addOverlay(polygon);           //增加多边形
    map.addOverlay(rectangle);         //增加矩形
    let mapViewObj = this.map.getViewport(markerPointArr);
    map.flyTo(mapViewObj.center, mapViewObj.zoom)
}

//清除覆盖物
function remove_overlay() {
    map.clearOverlays();
}