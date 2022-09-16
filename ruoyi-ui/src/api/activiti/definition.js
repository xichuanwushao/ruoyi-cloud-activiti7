import request from '@/utils/request'

// 查询modeler列表
export function listDefinition(query) {
  return request({
    url: '/danny/processDefinition/list',
    method: 'get',
    params: query
  })
}

// 查询请假详细
export function getDefinitionsByInstanceId(instanceId) {
  return request({
    url: '/danny/processDefinition/getDefinitions/' + instanceId,
    method: 'get'
  })
}

// 挂起激活转换
export function suspendOrActiveApply(data) {
  return request({
    url: '/danny/processDefinition/suspendOrActiveApply',
    method: 'post',
    data:data
  })
}


// 删除Modeler
export function delDefinition(id) {
  return request({
    url: '/danny/processDefinition/remove/' + id,
    method: 'delete'
  })
}


