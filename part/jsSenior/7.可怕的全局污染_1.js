function show() {
    web = '123'; // 如果其他引入该js的文件有war变量，则调用该函数会影响那个文件的war变量的值
    // var web = '123'; // 只在该作用域内有效
}