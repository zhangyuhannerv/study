let username = 'tom'
let age = 2
let sing = function () {
  console.log('i love jerry')
}

// 前提是变量和属性名字一致
let person = {
  username,
  age,
  sing,
}

person.sing()
