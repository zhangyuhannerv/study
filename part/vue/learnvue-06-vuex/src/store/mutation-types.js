// 为了防止在commit时写的字符串和定义的事件类型不一致，创建此文件，将所有的事件类型定义为常量
// 在store的mutation里定义提交名称时用常量名称
// 在组件中使用mutation提交事件时，也用常量名称
// 这样就统一起来了
export const INCREMENT = 'increment'
export const UPDATEINFO2 = 'updateInfo2'
export const UPDATEINFO3 = 'updateInfo3'
export const UPDATENAME = 'updateName'