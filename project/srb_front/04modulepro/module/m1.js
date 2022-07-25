// 第一种导出方式
// export let star = '王力宏'
// export function sing(title) {
//   console.log(title)
// }

// 第二种导出方式,效果和上面一样
let star = '王力宏'
function sing(title) {
  console.log(title)
}

export { star, sing }
