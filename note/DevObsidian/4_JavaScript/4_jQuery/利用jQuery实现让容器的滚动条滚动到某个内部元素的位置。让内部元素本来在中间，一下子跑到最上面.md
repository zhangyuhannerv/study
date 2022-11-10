```html
<ul class='parent' style="height:50px;overflow:auto">
    <li class='children' style="height:25px"></li>
    <li class='children' style="height:25px"></li>
    <li class='children' style="height:25px"></li>
    <li class='children' style="height:25px"></li>
</ul>
```
```js
$(".parent").scrollTop($(".children:eq(2)").offset().top - $(".parent").offset().top + $(".parent").scrollTop());
// 上面的例子就是让父容器的滚动条滚动，让第三个孩子跑到最上面
// 其中parent是父容器
// children是子元素。
```