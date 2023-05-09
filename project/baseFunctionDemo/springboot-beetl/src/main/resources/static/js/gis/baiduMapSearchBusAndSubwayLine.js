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

function markStation() {
    if (!stationArr || !stationArr.length) return
    let stationIcon = new BMapGL.Icon("/img/station.png", new BMapGL.Size(20, 20));
    stationArr.forEach(marker => {
        marker.setLabel(new BMapGL.Label(marker.getTitle(), {
            position: marker.getPosition(),
            offset: new BMapGL.Size(15, 0)
        }))

        marker.setIcon(stationIcon)
        map.addOverlay(marker)
    })
}

