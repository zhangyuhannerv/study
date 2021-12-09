1. 后端渲染: 服务器渲染整个页面。 服务器找到资源后用后台模板引擎直接生成html，利用java实现数据的动态展示。返回浏览器。浏览器直接展示页面
2. 前端渲染: 服务器返回静态页面，浏览器渲染html和css。同时利用ajax从服务器获取数据动态展示
3. 后端路由: 每个url对应着一个单独的页面。这个映射关系由后台服务器管理
4. 前端路由: （是区分普通前后端分离还是SPA(单页面富应用)重要指标)。 每个url对应着整体资源的一部分（在vue里可以理解为组件）。这个映射关系由前端来负责维护

***
更改url而不使页面整体刷新的两种办法

1.更改hash值。

```js
window.location.hash = 'bbb';
```

2.修改h5里面的history

```js
window.history.pushState({}, '', 'aaa');// 此时是向栈里面push数据。浏览器的返回和前进按钮是能点击的
window.history.replaceState({}, '', 'aaa');// 此时是替换栈顶的数据。浏览器的返回和前进按钮是点不了的
```

window.history是一个栈对象，浏览器的地址栏总是显示最后压入的url。 window.history有back(),forward()和go()方法。调用能够让地址栏显示某个url
***
vue-router能让你选择从以上两种方式里面选择哪种方式来改变url(默认的是hash的方式)。
