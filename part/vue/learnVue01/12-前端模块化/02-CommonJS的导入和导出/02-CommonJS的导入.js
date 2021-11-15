// 这里用let也可以
// var aaa = require('./01-CommonJS的导出.js');
// var flag = aaa.flag;
// var sum = aaa.sum;

// 上面的可以写成下面这种写法。这种写法称为对象的解构
var {flag, sum} = require('./01-CommonJS的导出.js');
sum(10, 20);// 这样就可以直接使用了

// 同样的。这里这么写也是没有任何意义的。因为没有底层的代码去解析这种语法究竟代表着什么
