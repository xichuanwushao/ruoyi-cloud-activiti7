package com.ruoyi.danny.mapper;

import com.ruoyi.danny.domain.ActWorkflowFormData;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 动态单Mapper接口
 * 
 * @author danny
 * @date 2020-11-02
 */
public interface ActWorkflowFormDataMapper
{

    /**
     * 查询动态单
     *
     * @param businessKey 动态单ID
     * @return 动态单
     */
    public List<ActWorkflowFormData> selectActWorkflowFormDataByBusinessKey(String businessKey);


    /**
     * 新增动态单
     *
     * @param
     * @return 结果
     */
    public int insertActWorkflowFormDatas(@Param("createBy") String createBy, @Param("ActWorkflowFormData")List<ActWorkflowFormData> ActWorkflowFormData, Date date ,String createName);

}
