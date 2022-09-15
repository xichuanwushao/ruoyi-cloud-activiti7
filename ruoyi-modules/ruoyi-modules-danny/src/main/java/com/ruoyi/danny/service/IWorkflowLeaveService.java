package com.ruoyi.danny.service;


import com.ruoyi.danny.domain.WorkflowLeave;

import java.util.List;

/**
 * 请假Service接口
 * 
 * @author danny
 * @date 2020-10-28
 */
public interface IWorkflowLeaveService 
{

    /**
     * 新增请假
     * 
     * @param workflowLeave 请假
     * @return 结果
     */
    public int insertWorkflowLeave(WorkflowLeave workflowLeave);


    List<WorkflowLeave> selectWorkflowLeaveAndTaskNameList(WorkflowLeave workflowLeave);

    /**
     * 查询请假
     *
     * @param id 请假ID
     * @return 请假
     */
    public WorkflowLeave selectWorkflowLeaveById(String id);

    /**
     * 修改请假
     *
     * @param workflowLeave 请假
     * @return 结果
     */
    public int updateWorkflowLeave(WorkflowLeave workflowLeave);


    /**
     * 查询请假列表
     *
     * @param workflowLeave 请假
     * @return 请假集合
     */
    List<WorkflowLeave> selectWorkflowLeaveList(WorkflowLeave workflowLeave);
}
