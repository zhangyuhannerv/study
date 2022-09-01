import request from '@/utils/request'

export default {
  getList() {
    return request({
      url: `/admin/core/lend/list`,
      method: 'get'
    })
  }
}
