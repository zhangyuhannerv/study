只需要给表格加上一下css即可，加上之后，使用templet可以自定义格式化html来填充每个td的高度
```css
/*右下方的表格的内容高度自适应*/
.layui-table-cell {
    display: table-cell;
    vertical-align: middle;
}
```
注意：加上之后，可能标题和内容的宽度对应不起来，只需要手动给每个col（表头对象）一个固定的宽度即可解决该问题