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


/************日期时间格式化方法start*************/

/**
 * 补位小算法。
 * 传入04，返回04
 * 传入4，返回04
 * 传入空，返回00
 * @param str
 * @returns {string}
 */
function padLeftZero(str) {
  return ("00" + str).substr(str.length);
}

/**
 * 整体的格式化时间和日期的公共方法
 * @param date
 * @param fmt
 * @returns {*}
 */
export function formatDate(date, fmt) {
  // 1.获取年份
  // y+->1个或者多个y
  // y*->0个或者多个y
  // y?->0个或者1个y
  // RegExp.$1是匹配到的字符串比如'yy'/'yyyy'
  if (/(y+)/.test(fmt)) {
    fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
  }

  // 2.获取月份和时间
  let o = {
    "M+": date.getMonth() + 1,
    "d+": date.getDate(),
    "h+": date.getHours(),
    "m+": date.getMinutes(),
    "s+": date.getSeconds()
  };

  for (let k in o) {
    if (new RegExp(`(${k})`).test(fmt)) {
      let str = o[k] + "";
      fmt = fmt.replace(RegExp.$1, RegExp.$1.length === 1 ? str : padLeftZero(str));
    }
  }

  return fmt;
}

/************日期时间格式化方法end*************/


