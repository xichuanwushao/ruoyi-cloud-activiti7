package com.ruoyi.danny.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.danny.domain.WorkflowLeave;
import com.ruoyi.danny.mapper.WorkflowLeaveMapper;
import com.ruoyi.danny.service.ISysUserService;
import com.ruoyi.danny.service.IWorkflowLeaveService;
import com.ruoyi.danny.util.SecurityUtil;
import com.ruoyi.danny.util.UuidUtil;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.model.LoginUser;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 请假Service业务层处理
 *
 * @author danny
 * @date 2020-10-28
 */
@Service
public class WorkflowLeaveServiceImpl implements IWorkflowLeaveService {

    @Autowired
    private SecurityUtil securityUtil;


    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private WorkflowLeaveMapper workflowLeaveMapper;

    @Autowired
    private TaskService taskService;


    @Autowired
    private ProcessRuntime processRuntime;
    /**
     * 新增请假
     *
     * @param workflowLeave 请假
     * @return 结果
     */
    @Override
    public int insertWorkflowLeave(WorkflowLeave workflowLeave) {

        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser sysUser = loginUser.getSysUser();
        securityUtil.logInAs(sysUser.getUserName());
        String id = UuidUtil.getShortUuid();
        workflowLeave.setId(id);
        workflowLeave.setCreateTime(DateUtils.getNowDate());
        //guanyu,zhaoyun
        String join = StringUtils.join(sysUserService.selectUserNameByPostCodeAndDeptId("se", sysUser.getDeptId()), ",");
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("leave")
                .withName(workflowLeave.getTitle())
                .withBusinessKey(id)
                .withVariable("deptLeader",join)
                .build());
        workflowLeave.setInstanceId(processInstance.getId());
        workflowLeave.setState("0");
        workflowLeave.setCreateName(sysUser.getNickName());
        workflowLeave.setCreateBy(SecurityUtils.getUsername());
        workflowLeave.setCreateTime(DateUtils.getNowDate());
        return workflowLeaveMapper.insertWorkflowLeave(workflowLeave);
    }


    /**
     * 查询请假列表带任务状态
     *
     * @param workflowLeave 请假
     * @return 请假
     */
    @Override
    public List<WorkflowLeave> selectWorkflowLeaveAndTaskNameList(WorkflowLeave workflowLeave) {
        List<WorkflowLeave> workflowLeaves = workflowLeaveMapper.selectWorkflowLeaveList(workflowLeave);
        System.out.println("0909808"+workflowLeaves);
        List<String> collect = workflowLeaves.parallelStream().map(wl -> wl.getInstanceId()).collect(Collectors.toList());
        System.out.println("asdadad"+collect);
        if(collect!=null&&!collect.isEmpty()) {
            List<Task> tasks = taskService.createTaskQuery().processInstanceIdIn(collect).list();
            workflowLeaves.forEach(
                    wl->{
                        Task task = tasks.parallelStream().filter(t -> t.getProcessInstanceId().equals(wl.getInstanceId())).findAny().orElse(null);
                        if (task != null) {
                            wl.setTaskName(task.getName());
                        }
                    }
            );
        }
        System.out.println("1212212"+workflowLeaves);
        return workflowLeaves;
    }


    /**
     * 查询请假
     *
     * @param id 请假ID
     * @return 请假
     */
    @Override
    public WorkflowLeave selectWorkflowLeaveById(String id) {
        return workflowLeaveMapper.selectWorkflowLeaveById(id);
    }


    /**
     * 修改请假
     *
     * @param workflowLeave 请假
     * @return 结果
     */
    @Override
    public int updateWorkflowLeave(WorkflowLeave workflowLeave) {
        workflowLeave.setUpdateTime(DateUtils.getNowDate());
        return workflowLeaveMapper.updateWorkflowLeave(workflowLeave);
    }

    /**
     * 查询请假列表
     *
     * @param workflowLeave 请假
     * @return 请假
     */
    @Override
    public List<WorkflowLeave> selectWorkflowLeaveList(WorkflowLeave workflowLeave) {
        return workflowLeaveMapper.selectWorkflowLeaveListByWorkflowLeaveAndDeptId(workflowLeave,SecurityUtils.getLoginUser().getSysUser().getDeptId());
    }
}
