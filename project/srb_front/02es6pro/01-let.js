// 声明变量
// var能重复声明变量
// var a;
// a = 1;
// var a = true
// console.log(a);

// // let变量不能重复声明
// let b
// b = 2
// let b = true
// console.log(b)


// // var没有块级作用域
// var flag = true// 形成是否结束
// if (flag) {
//   // 作用域
//   var star = 5

// }

// console.log(star);

// // let有块级作用域
// let flag = true// 形成是否结束
// if (flag) {
//   // 作用域
//   let star = 5

// }
// console.log(star);

// // var和let都具备函数级别的作用域
// function test1() {
//   var f1 = '函数test1的变量'
//   console.log(f1)
// }
// test1()
// console.log(f1)

// let不存在变量提升，var存在变量提升
// console.log(userName)
// var userName

console.log(userName)
let userName


