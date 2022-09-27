import request from '@/utils/request'

// 查询报销申请列表
export function listReim(query) {
  return request({
    url: '/danny/reim/list',
    method: 'get',
    params: query
  })
}

// 查询报销申请详细
export function getReim(reimId) {
  return request({
    url: '/danny/reim/' + reimId,
    method: 'get'
  })
}

// 新增报销申请
export function addReim(data) {
  return request({
    url: '/danny/reim',
    method: 'post',
    data: data
  })
}

// 修改报销申请
export function updateReim(data) {
  return request({
    url: '/danny/reim',
    method: 'put',
    data: data
  })
}

// 删除报销申请
export function delReim(reimId) {
  return request({
    url: '/danny/reim/' + reimId,
    method: 'delete'
  })
}
