

package com.ruoyi.danny.service.impl;

import com.github.pagehelper.Page;
import com.ruoyi.common.core.web.page.PageDomain;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.danny.domain.ActTaskDTO;
import com.ruoyi.danny.domain.ActWorkflowFormData;
import com.ruoyi.danny.domain.dto.ActWorkflowFormDataDTO;
import com.ruoyi.danny.service.IActTaskService;
import com.ruoyi.danny.service.IActWorkflowFormDataService;
import com.ruoyi.danny.util.SecurityUtil;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.model.LoginUser;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.bpmn.model.FormProperty;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.activiti.api.model.shared.model.VariableInstance;
@Service
public class ActTaskServiceImpl implements IActTaskService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskRuntime taskRuntime;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private IActWorkflowFormDataService actWorkflowFormDataService;

    @Autowired
    private ProcessRuntime processRuntime;

    @Override
    public Page<ActTaskDTO> selectProcessDefinitionList(PageDomain pageDomain) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser sysUser = loginUser.getSysUser();
        securityUtil.logInAs(sysUser.getUserName());
        Page<ActTaskDTO> list = new Page<ActTaskDTO>();
        org.activiti.api.runtime.shared.query.Page<Task> pageTasks = taskRuntime.tasks(Pageable.of((pageDomain.getPageNum() - 1) * pageDomain.getPageSize(), pageDomain.getPageSize()));
        List<Task> tasks = pageTasks.getContent();
        int totalItems = pageTasks.getTotalItems();
        list.setTotal(totalItems);
        if (totalItems != 0) {
            Set<String> processInstanceIdIds = tasks.parallelStream().map(t -> t.getProcessInstanceId()).collect(Collectors.toSet());
            List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().processInstanceIds(processInstanceIdIds).list();
            List<ActTaskDTO> actTaskDTOS = tasks.stream()
                    .map(t -> new ActTaskDTO(t, processInstanceList.parallelStream().filter(pi -> t.getProcessInstanceId().equals(pi.getId())).findAny().get()))
                    .collect(Collectors.toList());
            list.addAll(actTaskDTOS);

        }
        /****
         * 获取流程实例中的变量 并返回 这里取出变量中附件的地址
         */
        Page<ActTaskDTO> listAfter = new Page<ActTaskDTO>();
        for(ActTaskDTO actTaskDTO : list){
            List<VariableInstance> variableInstanceList =  processRuntime.variables(ProcessPayloadBuilder
                    .variables()
                    .withProcessInstanceId(actTaskDTO.getInstanceId())
                    .build()
            );
            for(VariableInstance vi : variableInstanceList){
                if("attachmentLink".equals(vi.getName())) { //获取附件
                    actTaskDTO.setAttachmentLink(vi.getValue());
                }
                if("assignee".equals(vi.getName())){        //获取申请人
                    actTaskDTO.setAssignee(vi.getValue());
                }
            }
            listAfter.add(actTaskDTO);
        }

        return listAfter;
    }


    @Override
    public List<String> formDataShow(String taskID) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser sysUser = loginUser.getSysUser();
        securityUtil.logInAs(sysUser.getUserName());
        Task task = taskRuntime.task(taskID);
/*  ------------------------------------------------------------------------------
            FormProperty_0ueitp2--__!!类型--__!!名称--__!!是否参数--__!!默认值
            例子：
            FormProperty_0lovri0--__!!string--__!!姓名--__!!f--__!!同意!!__--驳回
            FormProperty_1iu6onu--__!!int--__!!年龄--__!!s

            默认值：无、字符常量、FormProperty_开头定义过的控件ID
            是否参数：f为不是参数，s是字符，t是时间(不需要int，因为这里int等价于string)
            注：类型是可以获取到的，但是为了统一配置原则，都配置到
            */

        //注意!!!!!!!!:表单Key必须要任务编号一模一样，因为参数需要任务key，但是无法获取，只能获取表单key“task.getFormKey()”当做任务key
        UserTask userTask = (UserTask) repositoryService.getBpmnModel(task.getProcessDefinitionId())
                .getFlowElement(task.getFormKey());

        if (userTask == null) {
            return null;
        }
        List<FormProperty> formProperties = userTask.getFormProperties();
        List<String> collect = formProperties.stream().map(fp -> fp.getId()).collect(Collectors.toList());

        return collect;
    }


    @Override
    public int formDataSave(String taskID, List<ActWorkflowFormDataDTO> awfs) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser sysUser = loginUser.getSysUser();
        securityUtil.logInAs(sysUser.getUserName());
        Task task = taskRuntime.task(taskID);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();

        Boolean hasVariables = false;//没有任何参数
        HashMap<String, Object> variables = new HashMap<String, Object>();
        //前端传来的字符串，拆分成每个控件
        List<ActWorkflowFormData> acwfds = new ArrayList<>();
        for (ActWorkflowFormDataDTO awf : awfs) {
            ActWorkflowFormData actWorkflowFormData = new ActWorkflowFormData(processInstance.getBusinessKey(),awf, task);
            acwfds.add(actWorkflowFormData);
            //构建参数集合
            if(!"f".equals(awf.getControlIsParam())) {
                variables.put(awf.getControlId(), awf.getControlValue());
                hasVariables = true;
            }
        }//for结束
        if (task.getAssignee() == null) {
            taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
        }
        if (hasVariables) {
            //带参数完成任务
            taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(taskID)
                    .withVariables(variables)
                    .build());
        } else {
            taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(taskID)
                    .build());
        }


        //写入数据库
        return actWorkflowFormDataService.insertActWorkflowFormDatas(acwfds);
    }


}
