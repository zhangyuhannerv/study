// let 和 const是具有块作用域的，var没有、
// 以前封装在函数里(立即执行函数)，现在封装在块作用域里
{
    let $ = (window.$ = {});
    let c = 'abc';
    $.d = 'def';
    let e = 'zxc';
    $.getC = function() {
        return c;
    }
}