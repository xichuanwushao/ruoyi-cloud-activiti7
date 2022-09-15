package com.ruoyi.danny.controller;


import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/activitiHistory")
public class ActivitiHistoryController {


    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;



    @GetMapping("/searchApprovalBpmn")
    public void searchApprovalBpmn(String instanceId, HttpServletResponse response) throws UnsupportedEncodingException {

        response.setContentType("image/jpg");
//        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(instanceId+".jpg", "UTF-8"));
//        response.setDateHeader("expries", -1);
//        response.setHeader("Cache-Control", "no-cache");
//        response.setHeader("Pragma", "no-cache");
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
        BpmnModel bpmnModel;
        List activeActivityIds;
        if (task != null) {
            bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
            activeActivityIds = runtimeService.getActiveActivityIds(task.getExecutionId());
        } else {
            HistoricTaskInstance taskInstance = historyService.createHistoricTaskInstanceQuery().processInstanceId(instanceId).list().get(0);
            bpmnModel = repositoryService.getBpmnModel(taskInstance.getProcessDefinitionId());
            activeActivityIds = new ArrayList<>();
        }

        Map map = bpmnModel.getItemDefinitions();
        DefaultProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();
        String os = System.getProperty("os.name").toLowerCase();
        String font = "SimSun";
        if (os.startsWith("win")) {
            font = "宋体";
        }
        try (InputStream in = diagramGenerator.generateDiagram(bpmnModel, "jpg", activeActivityIds, activeActivityIds,
                font, font, font,
                processEngine.getProcessEngineConfiguration().getProcessEngineConfiguration().getClassLoader(), 1.0);
             BufferedInputStream bin = new BufferedInputStream(in);
             OutputStream out = response.getOutputStream();
             BufferedOutputStream bout = new BufferedOutputStream(out);
        ) {
            IOUtils.copy(bin, bout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
