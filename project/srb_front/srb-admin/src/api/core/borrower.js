import request from '@/utils/request'

export default {
  getPageList(page, limit, keyword) {
    return request({
      url: `/admin/core/borrower/list/${page}/${limit}`,
      method: 'get',
      params: { keyword }
    })
  }
}
