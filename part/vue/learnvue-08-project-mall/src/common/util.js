/**
 * 防抖函数
 * @param func
 * @param delay
 * @returns {(function(...[*]=): void)|*}
 */
export function debounce(func, delay = 500) {
  let timer = null
  // 这里是个闭包（函数）对timer有引用，所以timer一直没有销毁
  return function (...args) {
    if (timer) clearTimeout(timer)
    timer = setTimeout(() => {
      func.apply(this, args)
    }, delay)
  }
}

