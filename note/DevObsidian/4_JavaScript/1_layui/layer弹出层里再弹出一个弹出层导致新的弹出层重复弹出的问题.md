解决办法:在layui的layer配置json里给每个弹出层指定个不同的id即可
```js
let index = layer.open({
		type: 1,
		title: false,
		move: $('#uploadHead'),
		closeBtn: 0,
		area: ['5.1979rem', '2.5052rem'],
		content: $("#uploadFileModel"),
		id: 'layer1'
	})
layer.confirm('是否取消本次文件上传？',
				{
					skin: 'confirm-class',
					icon: 3,
					title: '提示',
					id: 'layer2'
				},
				function (index1) {// index1表示确认框代表的弹出层实例
					layer.closeAll();
})
```