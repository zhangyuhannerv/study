/*此时会报错。flag is not defined*/
// if (flag) {
//   console.log("小明时天才")
// }

// 导入之后就不报错了

// 导入方式1：这么分开导入也可以
/*import {flag} from "./aaa.js"
// 如果重复导入，那么会报错“Identifier 'flag' has already been declared”
import {flag} from "./aaa.js"
import {sum} from "./aaa.js"
import {a} from "./aaa.js"*/


// 导入方式2： 这么合在一起导入也可以
import {flag, sum, a, mul, Person} from "./aaa.js"


if (flag) {
  console.log("小明是天才")
  console.log(sum(20, 40))
  console.log(a);
  console.log(mul(2, 4))
}


let person = new Person();
person.run();

let person1 = new Person("x");
console.log(person1.x)

// 导入方式3：导入匿名
// 这个hhh随便取，它指代的是aaa.js里的export default所代表的东西
import hhh from './aaa.js'

// console.log(hhh)
// hhh(23)
new hhh().print();

// 导入方式4：统一全部导入（把aaa.js里的所有导出的东西全部导入)
// import {flag, num, mul................} from './aaa.js'// 这样写变量太多了，而且太长了，并且如果导入flag，那么后续就不能定义flag了
import * as moduleA from './aaa.js';

console.log('---------------------')
console.log(moduleA.flag);
new moduleA.Person().run();
console.log(moduleA.mul(10, 10));

new moduleA.default().print();// 这里能直接使用aaa.js里的export default导出的东西。但是不推荐这样。还是推荐另写一行导入给default命个名。然后再通过自己的命名使用这个default


