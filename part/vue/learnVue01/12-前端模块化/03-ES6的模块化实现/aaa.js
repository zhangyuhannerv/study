// 这里用var也行
let name = '小明'
let age = 18
// 不会有名命冲突，因为引入该script时加上了type='module'
var flag = true

function sum(num1, num2) {
  return num1 + num2
}

if (flag) {
  console.log(30, 20)
}

class people {

}


// 导出方式1：
// 这种写法也可以
export let a = 1;
export let b = 2;
export var c = 2;

// 导出方式2：
export {
  flag,
  sum,
  people
}

// 导出方式3（导出函数和类):
export function mul(num1, num2) {
  // 导出函数可以用这种写法也可以用2那种方式写在对象里
  return num1 * num2;
}

// es5里面定义类
// function Person() {
//
// }

// es6里面定义类
// class Person {
//
// }

export class Person {
  constructor(x, y) {
    this.x = x;
    this.y = y;
  }

  run() {
    console.log('在奔跑');
  }
}

// 导出方式4：
// export default(在一个模块里如果有的话，只能有一个)
// 某些情况下，一个模块中包含某个东西（变量，函数，类等等等)，我们不希望给这个东西名命，而是让导入者自己给这个东西名命

// const address = '北京市';
// export default address;

// export default function (argument) {
//   console.log(argument);
// }

export default class {
  print() {
    console.log('打印默认类')
  }
}

