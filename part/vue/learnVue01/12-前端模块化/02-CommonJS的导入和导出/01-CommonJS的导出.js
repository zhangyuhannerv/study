var name = '小明';
var age = '22';

function sum(num1, num2) {
  return num1 + num2;
}

var flag = true;
if (flag) {
  console.log(sum(10, 20));
}

// 导出的语法如下。
// 注意：现在的这种写法毫无意义。因为没有底层框架(如node,它会解析下面这种写法)的支撑。
// 这里主要演示CommonJs导出的语法如何写。因为会在后续的webpack里用到
module.exports = {
  // es6字面量增强写法
  flag,
  sum,
}


