// 小明写的第二个js文件
// if (flag) {
//   // 这里不会打印，因为小红把全局变量flag给改成false了
//   console.log('小明是天才')
// }


// 后来引进了闭包函数来解决全局变量污染的问题
// 但是产生了新的问题
// 小明之前写的flag找不到了
// 之前写的sum()函数也找不到了
// 要想解决，只能自己重新定义flag和sum()函数（这就降低了代码的复用性）
(function () {

  // moduleA是aaa.js里的全局变量
  // 1.想使用flag
  if (moduleA.flag) {
    // 这里不会打印，因为小红把全局变量flag给改成false了
    console.log('小明是天才')
  }

  // 2.想使用sum()
  console.log(moduleA.sum(20, 20))
})()
