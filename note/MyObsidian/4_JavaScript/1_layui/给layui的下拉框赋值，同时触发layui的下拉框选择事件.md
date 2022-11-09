前提：该选择框需要放在layui-form的表单下
```js
$("#citySerch").val(cityId);// 给下拉框赋值
Let filter=$('#citySerch').attr('lay-filter');//获取该元素的lay-filter属性
filter&&layui.event('form','select('+filter+')',{elem:$("#citySerch"),value:cityId});//触发该标签的select事件
form.render('select');// 重新渲染下拉框
```
