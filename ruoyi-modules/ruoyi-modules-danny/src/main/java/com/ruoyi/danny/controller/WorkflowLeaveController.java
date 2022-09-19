package com.ruoyi.danny.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.danny.domain.WorkflowLeave;
import com.ruoyi.danny.service.IWorkflowLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 请假Controller
 *
 * @author danny
 * @date 2020-10-28
 */
@RestController
@RequestMapping("/workflow/leave")
public class WorkflowLeaveController extends BaseController {

    @Autowired
    private IWorkflowLeaveService workflowLeaveService;

    /**
     * bpmn流程图片地址
     */
    @Value("${bpmn.domain}")
    public String domain;

    /**
     * 新增请假
     */
    @PostMapping
    public AjaxResult add(@RequestBody WorkflowLeave workflowLeave) {
        return toAjax(workflowLeaveService.insertWorkflowLeave(workflowLeave));
    }

    @GetMapping("/hello")
    public AjaxResult hello() {
        return AjaxResult.success("hello world");
    }


    /**
     * 查询请假列表
     */
    @GetMapping("/list")
    public TableDataInfo list(WorkflowLeave workflowLeave) {
        startPage();
        workflowLeave.setCreateBy(SecurityUtils.getUsername());
        List<WorkflowLeave> list = workflowLeaveService.selectWorkflowLeaveAndTaskNameList(workflowLeave);
        for(WorkflowLeave workflowLeave1: list){
            String url = domain+"/activitiHistory/searchApprovalBpmn?instanceId="+workflowLeave1.getInstanceId();
            workflowLeave1.setBpmnUrl(url);
        }
        return getDataTable(list);
    }

    /**
     * 获取请假详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(workflowLeaveService.selectWorkflowLeaveById(id));
    }

    /**
     * 查询请假列表
     */
    @GetMapping("/listAll")
    public TableDataInfo listAll(WorkflowLeave workflowLeave) {
        startPage();
        List<WorkflowLeave> list = workflowLeaveService.selectWorkflowLeaveList(workflowLeave);
        return getDataTable(list);
    }
}
