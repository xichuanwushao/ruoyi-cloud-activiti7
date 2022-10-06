import request from '@/utils/request'

// 查询task列表
export function listTask(query) {
  return request({
    url: '/danny/task/list',
    method: 'get',
    params: query
  })
}

// 查询表单
export function formDataShow(taskID) {
  return request({
    url: '/danny/task/formDataShow/'+taskID,
    method: 'get',
  })
}

// 查询表单
export function formDataSave(taskID,data) {
  return request({
    url: '/danny/task/formDataSave/'+taskID,
    method: 'post',
    data:data
  })
}




