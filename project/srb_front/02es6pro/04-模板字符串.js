// 1.声明一个字符串
let str1 = '我是字符串' // ""/''都可以声明一个普通字符串
let str2 = `我也是字符串`
console.log(str1)
console.log(str2)

// 2.原样输出
// ''不能隔行定义字符串
// ``能定义更行字符串
let html = '<ul><li>沈腾</li><li>马丽</li></ul>'
console.log(html)

let html1 = `
<ul>
  <li>沈腾</li>
  <li>马丽</li>
</ul>
`
console.log(html1)

// 3.变量拼接（插值表达式）
let star = '贾玲'
let out = `我喜欢${star}这个演员`
console.log(out)
