package com.ruoyi.danny.controller;

import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import cn.hutool.json.JSONUtil;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.danny.domain.WorkflowLeave;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.danny.domain.DanWorkflowReim;
import com.ruoyi.danny.service.IDanWorkflowReimService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 报销申请Controller
 * 
 * @author wuxiao
 * @date 2022-09-27
 */
@RestController
@RequestMapping("/workflow/reim")
public class DanWorkflowReimController extends BaseController
{
    @Autowired
    private IDanWorkflowReimService danWorkflowReimService;

    /**
     * bpmn流程图片地址
     */
    @Value("${bpmn.domain}")
    public String domain;

    /**
     * 查询报销申请列表
     */
    @RequiresPermissions("danny:reim:list")
    @GetMapping("/list")
    public TableDataInfo list(DanWorkflowReim danWorkflowReim)
    {
        startPage();
        danWorkflowReim.setCreateBy(SecurityUtils.getUsername());
        List<DanWorkflowReim> list = danWorkflowReimService.selectDanWorkflowReimList(danWorkflowReim);
        for(DanWorkflowReim workflowLeave1: list){
            String url = domain+"/activitiHistory/searchApprovalBpmn?instanceId="+workflowLeave1.getInstanceId();
            workflowLeave1.setBpmnUrl(url);
        }
        return getDataTable(list);
    }

    /**
     * 导出报销申请列表
     */
    @RequiresPermissions("danny:reim:export")
    @Log(title = "报销申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DanWorkflowReim danWorkflowReim)
    {
        List<DanWorkflowReim> list = danWorkflowReimService.selectDanWorkflowReimList(danWorkflowReim);
        ExcelUtil<DanWorkflowReim> util = new ExcelUtil<DanWorkflowReim>(DanWorkflowReim.class);
        util.exportExcel(response, list, "报销申请数据");
    }

    /**
     * 获取报销申请详细信息
     */
    @RequiresPermissions("danny:reim:query")
    @GetMapping(value = "/{reimId}")
    public AjaxResult getInfo(@PathVariable("reimId") String reimId)
    {
        return AjaxResult.success(danWorkflowReimService.selectDanWorkflowReimByReimId(reimId));
    }

    /**
     * 新增报销申请
     */
    @RequiresPermissions("danny:reim:add")
    @Log(title = "报销申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DanWorkflowReim danWorkflowReim)
    {
        return toAjax(danWorkflowReimService.insertDanWorkflowReim(danWorkflowReim));
    }

    /**
     * 修改报销申请
     */
    @RequiresPermissions("danny:reim:edit")
    @Log(title = "报销申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DanWorkflowReim danWorkflowReim)
    {
        return toAjax(danWorkflowReimService.updateDanWorkflowReim(danWorkflowReim));
    }

    /**
     * 删除报销申请
     */
    @RequiresPermissions("danny:reim:remove")
    @Log(title = "报销申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{reimIds}")
    public AjaxResult remove(@PathVariable String[] reimIds)
    {
        return toAjax(danWorkflowReimService.deleteDanWorkflowReimByReimIds(reimIds));
    }


    @GetMapping(value = "/searchReimById/{reimId}")
    @Operation(summary = "根据ID查询报销单")
    public AjaxResult searchReimById(@PathVariable("reimId") String reimId){
        HashMap param= new HashMap();
        param.put("userId", SecurityUtils.getUserId());
        HashMap map=danWorkflowReimService.searchReimById(reimId);
        logger.info(map.get("danWorkflowReimgoodsList")+"");
        return AjaxResult.success(map);
    }
}
