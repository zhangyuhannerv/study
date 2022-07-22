// 1.数组的解构赋值
const F4 = ['小沈阳', '宋小宝', '刘能', '赵四']
console.log(F4)
// 传统赋值
// let shenyang = F4[0]
// let xiaobao = F4[1]'
let a = '2'
// 解构赋值
// 相同索引匹配
let [shenyang, xiaobao, liuneng, zhaosi] = F4
console.log(shenyang)
console.log(xiaobao)
console.log(liuneng)
console.log(zhaosi)

// 2.对象的解构赋值
const abc = {
  username: '赵本山',
  age: '不详',
  yanxiaopin() {
    console.log('演小品')
  },
  sex: '男',
  hometown: '辽宁',
}

// 传统赋值
// let username = abc.username
// let age = abc.age
// let yanxiaopin = abc.yanxiaopin

// 解构赋值
// 相同属性名匹配
let { username, age, yanxiaopin } = abc

console.log(username)
console.log(age)
yanxiaopin()
