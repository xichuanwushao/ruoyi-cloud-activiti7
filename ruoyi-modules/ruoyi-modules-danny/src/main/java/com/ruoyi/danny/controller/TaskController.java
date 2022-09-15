package com.ruoyi.danny.controller;


import com.github.pagehelper.Page;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.PageDomain;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.core.web.page.TableSupport;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.danny.domain.ActTaskDTO;
import com.ruoyi.danny.domain.dto.ActWorkflowFormDataDTO;
import com.ruoyi.danny.service.IActTaskService;
import com.ruoyi.danny.util.SecurityUtil;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.model.LoginUser;
import org.activiti.api.task.runtime.TaskRuntime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping("/task")
public class TaskController extends BaseController {
    @Autowired
    private TaskRuntime taskRuntime;

    @Autowired
    private IActTaskService actTaskService;

    @Autowired
    private SecurityUtil securityUtil;

    //获取我的代办任务
    @GetMapping(value = "/list")
    public TableDataInfo getTasks() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Page<ActTaskDTO> hashMaps = actTaskService.selectProcessDefinitionList(pageDomain);
         return getDataTable(hashMaps);


    }


    //渲染表单
    @GetMapping(value = "/formDataShow/{taskID}")
    public AjaxResult formDataShow(@PathVariable("taskID") String taskID) {

        return AjaxResult.success(actTaskService.formDataShow(taskID));
    }


    //保存表单
    @PostMapping(value = "/formDataSave/{taskID}")
    public AjaxResult formDataSave(@PathVariable("taskID") String taskID,
                                   @RequestBody   List<ActWorkflowFormDataDTO> formData ) throws ParseException {
        return toAjax(actTaskService.formDataSave(taskID, formData));

    }
}
