function add(num1, num2) {
  return num1 + num2
}

function mul(num1, num2) {
  return num1 * num2
}

// 1先用commonJS导出
module.exports = {
  add,
  mul
}

