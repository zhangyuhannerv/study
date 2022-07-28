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
  },

  // 删除积分等级
  removeById(id) {
    return request({
      url: '/admin/core/integralGrade/remove/' + id,
      method: 'delete'
    })
  },

  // 保存积分等级
  save(integralGrade) {
    return request({
      url: '/admin/core/integralGrade/save',
      method: 'post',
      data: integralGrade
    })
  },
  // 根据id获取积分等级信息
  gateById(id) {
    return request({
      url: '/admin/core/integralGrade/get/' + id,
      method: 'get'
    })
  },
  // 更新积分等级
  updateById(integralGrade) {
    return request({
      url: '/admin/core/integralGrade/update',
      method: 'put',
      data: integralGrade
    })
  }
}
