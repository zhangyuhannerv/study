// 异步编程解决方案（文件读取，ajax）

// 这里以文件读取为例
// 引入node.js中的本地文件扩展功能
const fs = require('fs')

// 实例化Promise
// Promise对象有三种状态，初始化状态，成功，失败
// resolve和reject是函数类型的参数，通过他们两个，可以把promise的状态从初始化改为成功或者失败
const p = new Promise((resolve, reject) => {
  // 执行异步操作
  // 第一个参数：读取的文件路径
  // 第二个参数：读取过程中对响应结果的处理
  fs.readFile('../01nodepro/a.txt', (err, data) => {
    // 当文件读取失败时的错误对象
    if (err) {
      reject(err) // 将promise的状态改为失败
      return
    }

    // 当文件读取成功时的文件的内容
    resolve(data.toString()) // 将promise的状态改为成功
  })
})

p.then((response) => {
  // promise的状态的为成功时
  console.log(response)
}).catch((err) => {
  // promise的状态为失败时
  console.log('出错了')
  console.error(err)
})
