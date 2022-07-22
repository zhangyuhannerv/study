// // js没有函数的重载
// // 同名函数总是后定义的生效
// function add(a) {
//   console.log(a)
//   return 100 + a
// }

// function add() {
//   return 100
// }

// let result = add(10)
// console.log(result)

// 函数的默认值
// b没传值，就是0，传了就是传过来的值
function add(a, b = 0) {
  console.log(a)
  console.log(b)
  return 100 + a + b
}

let result = add(10, 0)
console.log(result)
