暂时只对input和textarea有效

select没经过测试，不知道对select是否也有效

```js
$('textarea').bind('input propertychange', function () {
    // do sth
});
```