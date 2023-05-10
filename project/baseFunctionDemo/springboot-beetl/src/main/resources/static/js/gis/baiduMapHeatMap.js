/**
 *  创建MapVGL图层管理器
 */
var view = new mapvgl.View({
    map: map
});

/**
 * 创建可视化图层，并添加到图层管理器中
 */
var heatmap = new mapvgl.HeatmapLayer({
    size: 600, // 单个点绘制大小
    max: 40, // 最大阈值
    height: 0, // 最大高度，默认为0
    unit: 'm', // 单位，m:米，px: 像素
    gradient: { // 对应比例渐变色
        0.25: 'rgba(0, 0, 255, 1)',
        0.55: 'rgba(0, 255, 0, 1)',
        0.85: 'rgba(255, 255, 0, 1)',
        1: 'rgba(255, 0, 0, 1)'
    }
});

view.addLayer(heatmap);

// 准备好规范化坐标数据
var data = []
$.ajax({
    url: "/heatMapData/heatMapData.txt",
    async: false,
    success: function (res) {
        let arr = res.split('\n')
        arr.forEach(e => {
            if (e) {
                let item = e.split(',');
                data.push({
                    geometry: {
                        type: 'Point',
                        coordinates: [item[0], item[1]]
                    },
                    properties: {
                        count: item[2]
                    }
                });
            }
        })
    }
});

function showHeat() {
    map.setMapStyleV2({
        styleId: '70ebf6340b0803826ed7510f080b2e07'
    });
    map.setZoom(11)
    heatmap.setData(data);
}

function hideHeat() {
    map.setMapStyleV2({
        styleId: '26cac412c6c5fd8298602f898c4970d6'
    });
    map.setZoom(15)
    heatmap.setData([])
}

