// 注意导入的时候和之前讲导入语法的时候有点区别，不用写.js。webpack会根据名称直接找到对应的js文件

// 1.先用commonJS导入
const {add, mul} = require("./mathUtil")

console.log(add(20, 30))
console.log(mul(20, 30))

// 2.使用ES6导入
import {name, age, height} from "./info"

console.log(name)
console.log(age)
console.log(height)

// 综上，可以看到。在webpack下，使用任何的模块化规范（包括AMD，CMD，ES6，commonJS....）都可以，甚至可以同时使用。
