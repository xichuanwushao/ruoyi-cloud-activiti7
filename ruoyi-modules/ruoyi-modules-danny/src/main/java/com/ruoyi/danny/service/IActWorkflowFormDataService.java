package com.ruoyi.danny.service;


import com.ruoyi.danny.domain.ActWorkflowFormData;

import java.util.List;

/**
 * 动态单Service接口
 * 
 * @author danny
 * @date 2020-11-02
 */
public interface IActWorkflowFormDataService
{

    public List<ActWorkflowFormData> selectActWorkflowFormDataByBusinessKey(String businessKey);


    /**
     * 新增动态单集合
     * @param ActWorkflowFormDatas 动态表单集合
     * @return
     */
    public int insertActWorkflowFormDatas(List<ActWorkflowFormData> ActWorkflowFormDatas);
}
