package com.ruoyi.danny.mapper;

import com.ruoyi.danny.domain.WorkflowLeave;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 请假Mapper接口
 * 
 * @author danny
 * @date 2020-10-28
 */
public interface WorkflowLeaveMapper 
{

    /**
     * 新增请假
     * 
     * @param workflowLeave 请假
     * @return 结果
     */
    public int insertWorkflowLeave(WorkflowLeave workflowLeave);


    /**
     * 查询请假列表
     *
     * @param workflowLeave 请假
     * @return 请假集合
     */
    public List<WorkflowLeave> selectWorkflowLeaveList(WorkflowLeave workflowLeave);

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
     * 查询请假列表根据部门编号和WorkflowLeave
     *
     * @param workflowLeave 请假
     * @return 请假集合
     */
    public List<WorkflowLeave> selectWorkflowLeaveListByWorkflowLeaveAndDeptId(@Param("workflowLeave")WorkflowLeave workflowLeave,@Param("deptId") Long deptId);

    /**
     * 查询请假
     *
     * @param instanceId 请假ID
     * @return 请假
     */
    public WorkflowLeave selectWorkflowLeaveByInstanceId(String instanceId);


    /**
     * 删除请假
     *
     * @param id 请假ID
     * @return 结果
     */
    public int deleteWorkflowLeaveById(String id);

    /**
     * 批量删除请假
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWorkflowLeaveByIds(String[] ids);
}
