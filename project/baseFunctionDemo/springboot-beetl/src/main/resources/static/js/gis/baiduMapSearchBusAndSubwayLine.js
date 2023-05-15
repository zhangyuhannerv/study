var pointArr
var stationArr
var busline = new BMapGL.BusLineSearch(map, {
    renderOptions: {map: map,},
    onGetBusListComplete: function (result) {
        if (result) {
            var fstLine = result.getBusListItem(0);//获取第一个公交列表显示到map上
            busline.getBusLine(fstLine);
        }
    },
    onPolylinesSet: function (polyLine) {
        pointArr = polyLine.getPath()
    },
    onMarkersSet: function (markerArr) {
        stationArr = markerArr;
        stationArr.forEach(e => {
            map.removeOverlay(e)
        })
    }
});

function busSearch() {
    var busName = '大兴机场线';
    busline.getBusList(busName);
}

function markHalfSearchLine() {
    if (!pointArr || !pointArr.length) return
    let halfLine = new BMapGL.Polyline(pointArr.slice(0, pointArr.length / 2),
        {
            strokeColor: "red",
            strokeWeight: 15,
            strokeOpacity: 1
        });   //创建折线
    map.addOverlay(halfLine);
}

var size1 = new BMapGL.Size(5, 5);
var size2 = new BMapGL.Size(10, 10);
var size3 = new BMapGL.Size(15, 15);
var size4 = new BMapGL.Size(20, 20);
var size5 = new BMapGL.Size(35, 35);
var size6 = new BMapGL.Size(40, 40);
var size7 = new BMapGL.Size(45, 45);

var size8 = new BMapGL.Size(15, 15);
var size9 = new BMapGL.Size(10, 10);
var size10 = new BMapGL.Size(5, 5);
var size11 = new BMapGL.Size(0, 0);
var size12 = new BMapGL.Size(-15, -15);
var size13 = new BMapGL.Size(-20, -20);
var size14 = new BMapGL.Size(-25, -25);

var sizeArr = []
sizeArr.push(size1)
sizeArr.push(size2)
sizeArr.push(size3)
sizeArr.push(size4)
sizeArr.push(size5)
sizeArr.push(size6)
sizeArr.push(size7)

var offsetArr = []
sizeArr.push(size8)
sizeArr.push(size9)
sizeArr.push(size10)
sizeArr.push(size11)
sizeArr.push(size12)
sizeArr.push(size13)
sizeArr.push(size14)

var sizeIndex = 3
var stationIcon = new BMapGL.Icon("/img/station.png", sizeArr[3]);

function markStation() {
    if (!stationArr || !stationArr.length) return

    stationArr.forEach(marker => {
        marker.setLabel(new BMapGL.Label(marker.getTitle(), {
            position: marker.getPosition(),
            offset: new BMapGL.Size(15, 0)
        }))

        marker.setIcon(stationIcon)
        map.addOverlay(marker)
    })
}

function openIconDynamic() {
    let tempZoomLevel
    map.addEventListener('zoomend', function (type, target) {
        let currentZoomLevel = parseInt(type.target.zoomLevel)
        if (tempZoomLevel == null) {
            tempZoomLevel = currentZoomLevel
        }
        let dif = currentZoomLevel - tempZoomLevel
        let currentSizeIndex = sizeIndex + dif;
        if (currentSizeIndex >= sizeArr.length) {
            currentSizeIndex = sizeArr.length - 1
        }
        if (currentSizeIndex < 0) {
            currentSizeIndex = 0
        }

        stationIcon.setSize(sizeArr[currentSizeIndex])
        stationIcon.setImageSize(sizeArr[currentSizeIndex])

        stationArr.forEach(marker => {
            marker.setIcon(stationIcon)
        })
    });
}

function closeIconDynamic() {
    map.addEventListener('zoomend', function () {
        stationIcon.setSize(sizeArr[3])
        stationIcon.setImageSize(sizeArr[3])

        stationArr.forEach(marker => {
            marker.setIcon(stationIcon)
        })
    })
}



