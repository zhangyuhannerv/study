```js
window.onresize = function () {  
    let chart = echarts.init(document.getElementById("id"))
    chart.resize()
}
```