

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
         * ?????????????????????????????? ????????? ????????????????????????????????????
         */
        Page<ActTaskDTO> listAfter = new Page<ActTaskDTO>();
        for(ActTaskDTO actTaskDTO : list){
            List<VariableInstance> variableInstanceList =  processRuntime.variables(ProcessPayloadBuilder
                    .variables()
                    .withProcessInstanceId(actTaskDTO.getInstanceId())
                    .build()
            );
            for(VariableInstance vi : variableInstanceList){
                if("attachmentLink".equals(vi.getName())) { //????????????
                    actTaskDTO.setAttachmentLink(vi.getValue());
                }
                if("assignee".equals(vi.getName())){        //???????????????
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
            FormProperty_0ueitp2--__!!??????--__!!??????--__!!????????????--__!!?????????
            ?????????
            FormProperty_0lovri0--__!!string--__!!??????--__!!f--__!!??????!!__--??????
            FormProperty_1iu6onu--__!!int--__!!??????--__!!s

            ?????????????????????????????????FormProperty_????????????????????????ID
            ???????????????f??????????????????s????????????t?????????(?????????int???????????????int?????????string)
            ?????????????????????????????????????????????????????????????????????????????????
            */

        //??????!!!!!!!!:??????Key????????????????????????????????????????????????????????????key??????????????????????????????????????????key???task.getFormKey()???????????????key
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

        Boolean hasVariables = false;//??????????????????
        HashMap<String, Object> variables = new HashMap<String, Object>();
        //????????????????????????????????????????????????
        List<ActWorkflowFormData> acwfds = new ArrayList<>();
        for (ActWorkflowFormDataDTO awf : awfs) {
            ActWorkflowFormData actWorkflowFormData = new ActWorkflowFormData(processInstance.getBusinessKey(),awf, task);
            acwfds.add(actWorkflowFormData);
            //??????????????????
            if(!"f".equals(awf.getControlIsParam())) {
                variables.put(awf.getControlId(), awf.getControlValue());
                hasVariables = true;
            }
        }//for??????
        if (task.getAssignee() == null) {
            taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
        }
        if (hasVariables) {
            //?????????????????????
            taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(taskID)
                    .withVariables(variables)
                    .build());
        } else {
            taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(taskID)
                    .build());
        }


        //???????????????
        return actWorkflowFormDataService.insertActWorkflowFormDatas(acwfds);
    }


}
