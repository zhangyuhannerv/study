// 注意导入的时候和之前讲导入语法的时候有点区别，不用写.js。webpack会根据名称直接找到对应的js文件

// 1.先用commonJS导入
const {add, mul} = require("./js/mathUtil")

console.log(add(20, 30))
console.log(mul(20, 30))

// 2.使用ES6导入
import {name, age, height} from "./js/info"

console.log(name)
console.log(age)
console.log(height)

// 综上，可以看到。在webpack下，使用任何的模块化规范（包括AMD，CMD，ES6，commonJS....）都可以，甚至可以同时使用。

// 3.依赖css文件（将css作为module)
require('./css/normal.css')
// 4.依赖less文件
// import('./css/special.less')// 这种es6导入css的方式不行。目前还没有找到es6导入css模块的正确写法
require('./css/special.less')
