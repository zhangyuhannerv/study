// 引入axios的初始化实例
import request from '@/utils/request'

// 导出默认模块
export default {
  // 定义模块成员

  // 获取积分列表
  list() {
    // 调用axios实例，发送ajax请求
    return request({
      url: '/admin/core/integralGrade/list',
      method: 'get'
    })
  }
}
