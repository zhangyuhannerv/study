// 小红
// var name = '小红';
// var flag = false;
// console.log(name);

// 后来引进了闭包函数来解决全局变量污染的问题

// 以后唯一要注意的是模块的名字不要一样
var moduleB = (function () {

  var name = '小红';
  var flag = false;
  console.log(name);

  // 小红也来个返回模块对象
  var obj = {};
  obj.flag = flag;
  return obj;
})()
