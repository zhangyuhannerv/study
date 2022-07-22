let person = {
  name: '王路飞',
  age: 19,
}

// let someone = person // 引用传递
let someone = { ...person } // 可以理解为对象的克隆，此时person和someone有两个各自独立的内存地址

console.log(person)
console.log(someone)

someone.name = '乔巴'

console.log(person)
console.log(someone)
