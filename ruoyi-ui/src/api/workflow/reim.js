import request from '@/utils/request'

// 查询请假列表
export function listReim(query) {
  return request({
    url: '/danny/workflow/reim/list',
    method: 'get',
    params: query
  })
}
// 查询请假列表
export function listReimAll(query) {
  return request({
    url: '/danny/workflow/reim/listAll',
    method: 'get',
    params: query
  })
}

// 查询请假详细
export function getReim(id) {
  return request({
    url: '/danny/workflow/reim/' + id,
    method: 'get'
  })
}


// 新增请假
export function addReim(data) {
  return request({
    url: '/danny/workflow/reim',
    method: 'post',
    data: data
  })
}

// 修改请假
export function updateReim(data) {
  return request({
    url: '/danny/workflow/reim',
    method: 'put',
    data: data
  })
}

// 删除请假
export function delReim(id) {
  return request({
    url: '/danny/workflow/reim/' + id,
    method: 'delete'
  })
}

// 导出请假
export function exportReim(query) {
  return request({
    url: '/danny/workflow/reim/export',
    method: 'get',
    params: query
  })
}
