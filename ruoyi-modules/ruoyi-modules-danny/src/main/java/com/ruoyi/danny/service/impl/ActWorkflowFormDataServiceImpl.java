package com.ruoyi.danny.service.impl;

import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.danny.domain.ActWorkflowFormData;
import com.ruoyi.danny.mapper.ActWorkflowFormDataMapper;
import com.ruoyi.danny.service.IActWorkflowFormDataService;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * 动态单Service业务层处理
 * 
 * @author danny
 * @date 2020-11-02
 */
@Service
public class ActWorkflowFormDataServiceImpl implements IActWorkflowFormDataService
{
    @Autowired
    private ActWorkflowFormDataMapper actWorkflowFormDataMapper;


    @Override
    public List<ActWorkflowFormData> selectActWorkflowFormDataByBusinessKey(String businessKey){
        return actWorkflowFormDataMapper.selectActWorkflowFormDataByBusinessKey(businessKey);
    }

    @Override
    public int insertActWorkflowFormDatas(List<ActWorkflowFormData> ActWorkflowFormDatas) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser sysUser = loginUser.getSysUser();
        return actWorkflowFormDataMapper.insertActWorkflowFormDatas(sysUser.getUserName(), ActWorkflowFormDatas, new Date(),sysUser.getNickName());

    }


}
