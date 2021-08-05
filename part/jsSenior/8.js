// var web = 'hdcms.com';
// 使用立即执行函数可以随便定义变量，不用担心对其他变量的影响
(function() {
    var $ = (window.$ = {});
    $.web = 'hdcms';
    var url = 'hdcms.com';
    $.getUrl = function() {
        return url;
    }
    var site = 'abc';
}.bind(window)());