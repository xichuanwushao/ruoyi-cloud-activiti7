package com.ruoyi.danny.controller;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.danny.domain.DanWorkflowReim;
import com.ruoyi.danny.domain.WorkflowLeave;
import com.ruoyi.danny.service.IWorkflowLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    @RequiresPermissions("workflow:leave:add")
    @Log(title = "请假", businessType = BusinessType.INSERT)
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
    @RequiresPermissions("workflow:leave:list")
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
    @RequiresPermissions("workflow:leave:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(workflowLeaveService.selectWorkflowLeaveById(id));
    }

    /**
     * 查询请假列表
     */
    @RequiresPermissions("workflow:leave:list")
    @GetMapping("/listAll")
    public TableDataInfo listAll(WorkflowLeave workflowLeave) {
        startPage();
        List<WorkflowLeave> list = workflowLeaveService.selectWorkflowLeaveList(workflowLeave);
        return getDataTable(list);
    }


    /**
     * 获取请假详细信息
     */
    @RequiresPermissions("workflow:leave:query")
    @GetMapping(value = "ByInstanceId/{instanceId}")
    public AjaxResult getInfoByInstanceId(@PathVariable("instanceId") String instanceId) {
        return AjaxResult.success(workflowLeaveService.selectWorkflowLeaveByInstanceId(instanceId));
    }


    /**
     * 修改请假
     */
    @RequiresPermissions("workflow:leave:edit")
    @Log(title = "请假", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorkflowLeave workflowLeave) {
        return toAjax(workflowLeaveService.insertWorkflowLeave(workflowLeave));
    }

    /**
     * 删除请假
     */
    @RequiresPermissions("workflow:leave:remove")
    @Log(title = "请假", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(workflowLeaveService.deleteWorkflowLeaveByIds(ids));
    }
    /**
     * 导出列表
     */
    @RequiresPermissions("workflow:leave:export")
    @Log(title = "请假申请", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response, WorkflowLeave workflowLeave)
    {
        List<WorkflowLeave> list = workflowLeaveService.selectWorkflowLeaveList(workflowLeave);
        System.out.println("list = " + list + ", workflowLeave = " + workflowLeave);
        ExcelUtil<WorkflowLeave> util = new ExcelUtil<WorkflowLeave>(WorkflowLeave.class);
        util.exportExcel(response, list, "请假申请数据");
    }

}
