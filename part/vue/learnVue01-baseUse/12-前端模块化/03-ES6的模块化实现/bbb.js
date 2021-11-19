// import {flag, sum} from "./aaa.js";
// 注意：如果导入了flag。下面又重新定义了flag的化，那么会报错“Identifier 'flag' has already been declared”

console.log('--bbb.js----')
// 这里用var也行
let name = '小红'
let age = 20
// 不会有名命冲突，因为引入该script时加上了type='module'
var flag = false
console.log(flag)
console.log('--bbb.js----')

