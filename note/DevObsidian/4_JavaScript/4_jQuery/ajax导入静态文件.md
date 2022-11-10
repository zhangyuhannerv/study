```js
$.ajax({
    type: 'get',
    url: Hussar.ctxPath + '/static/js/homePage/map.json', // 文件相对地址（相对于使用这个js脚本的html文件,非常重要，仔细理解这句话）
    dataType: 'json', // 类型,文件里定义的变量的类型
    async: false,
    success: function (data) {
        Lmap = data;
    }
})
```