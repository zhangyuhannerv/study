// 小明
// var name = '小明';
// var age = '22';
//
// function sum(num1, num2) {
//   return num1 + num2;
// }
//
// var flag = true;
// if (flag) {
//   console.log(sum(10, 20));
// }

// 为了解决全局变量污染，后来引进来闭包函数

// 以后唯一要注意的是模块的名字不要一样
// moduleA就是最原始的模块化(也是模块化最基础的封装),事实上模块化还有很多更高级的话题
// 后续前端的模块化开发已经有了很多既有的规范，以及对应的实现方案，所以这种原始的模块化也很少使用
// 常用的模块化规范：CommonJS,AMD,CMD,还有ES6的Modules
// CommonJS的实现最出名的就是node
var moduleA = (function () {
  var name = '小明';
  var age = '22';

  function sum(num1, num2) {
    return num1 + num2;
  }

  var flag = true;
  if (flag) {
    console.log(sum(10, 20));
  }

  // 为了解决在其他的js里引用不了flag和sum()的问题
  var obj = {};
  obj.flag = flag;
  obj.sum = sum;

  return obj;// 返回的obj赋值给全局变量moduleA
})()
