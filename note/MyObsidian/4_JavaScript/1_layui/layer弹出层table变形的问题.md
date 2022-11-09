解决方案1
前提：需要给每个表头设置合适的宽度
效果：弹出层宽度根据渲染出来的表格宽度自适应，高度固定（百分比/具体的数值都可以）
```css
.dataDetailLayer {
  display: none;
  padding: 20px;
}
```
```html
<div class="dataDetailLayer">
  <table id="dataDetailTable" lay-filter="dataDetailTable"></table>
</div>
```
```js
table.on('tool(dataTable)', function (obj) { // 注：test 是 table 原始标签的属性 lay-filter="对应的值"
  let data = obj.data; //获得当前行数据
  let id = data.id
  let layEvent = obj.event;
  if (layEvent === 'view') { //查看
    initDataDetailTable(data)
  } 
})

function initDataDetailTable(data) {
  let loadLayer = layer.msg('正在获取数据', {icon: 16, time: 0});
  let id = data.id
  let cols;
  if (data.xianbie) {
    // 当宽度超过当前窗口的宽度时，可以加上下面这行代码。让弹窗宽度自适应为80%
    // 但是注意：此时表头右固定会失效
   	// $(".dataDetailLayer").width($(document.body).width() * 0.8)
    cols = [[ //表头
      {type: 'numbers', title: '序号', width: 80}
      , {field: 'yskm', title: '预算科目', align: 'center', width: 90}
      , {field: 'wlsx', title: '物料属性', align: 'center', width: 110}
      , {field: 'wlbm', title: '物资编码', align: 'center', width: 140}
      , {field: 'wlmc', title: '物料名称', align: 'center', width: 120}
      , {field: 'ggxh', title: '规格型号', align: 'center', width: 120}
      , {field: 'dw', title: '计量单位', align: 'center', width: 90}
      , {field: 'dj', title: '预算单价', align: 'center', width: 90}
      , {field: 'sl', title: '数量', align: 'center', width: 90}
      , {field: 'hj', title: '合计', align: 'center', width: 80}
      , {field: 'slOne', title: '第一季度', align: 'center', width: 90}
      , {field: 'slTwo', title: '第二季度', align: 'center', width: 90}
      , {field: 'slThree', title: '第三季度', align: 'center', width: 90}
      , {field: 'slFour', title: '第四季度', align: 'center', width: 90}
      , {field: 'lrPer', title: '录入人', align: 'center', width: 80}

    ]]
  } else {
    cols = [[ //表头
      {type: 'numbers', title: '序号', rowspan: 2, width: 80}
      , {field: 'yskm', title: '预算科目', align: 'center', rowspan: 2, width: 90}
      , {field: 'wlsx', title: '物料属性', align: 'center', rowspan: 2, width: 110}
      , {field: 'wlbm', title: '物资编码', align: 'center', rowspan: 2, width: 140}
      , {field: 'wlmc', title: '物料名称', align: 'center', rowspan: 2, width: 120}
      , {field: 'ggxh', title: '规格型号', align: 'center', rowspan: 2, width: 120}
      , {field: 'dw', title: '计量单位', align: 'center', rowspan: 2, width: 90}
      , {field: 'dj', title: '预算单价', align: 'center', rowspan: 2, width: 90}
      , {field: 'sl', title: '数量', align: 'center', rowspan: 2, width: 90}
      , {field: 'hj', title: '合计', align: 'center', rowspan: 2, width: 80}
      , {title: '合计需求量', align: 'center', colspan: 3}
      , {field: 'lrPer', title: '录入人', align: 'center', rowspan: 2, width: 80}

    ], [ //表头
      {field: 'slYf', title: '燕房', align: 'center', width: 80}
      , {field: 'slJc', title: '机场', align: 'center', width: 80}
      , {field: 'sl19h', title: '19号', align: 'center', width: 80}
    ]]
  }
  table.render({
    elem: '#dataDetailTable'
    , url: Hussar.ctxPath + '/tYwglYsXlfinfo/list'
    , even: true
    , cols
    , where: {
      id,
    }, done() {
      layer.close(loadLayer)
      layer.open({
        title: data.jcmc,
        type: 1,
        content: $(".dataDetailLayer"),
        area: ['auto', '80%'],
        btn: ['关闭'],
        yes(index) {
          layer.close(index)
        },

      })
    }
  });
}
```