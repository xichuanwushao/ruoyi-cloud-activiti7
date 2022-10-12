package com.ruoyi.danny.service.impl;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.danny.domain.WorkflowLeave;
import com.ruoyi.danny.service.ISysUserService;
import com.ruoyi.danny.util.SecurityUtil;
import com.ruoyi.danny.util.UuidUtil;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.model.LoginUser;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.danny.domain.DanWorkflowReimgoods;
import com.ruoyi.danny.mapper.DanWorkflowReimMapper;
import com.ruoyi.danny.domain.DanWorkflowReim;
import com.ruoyi.danny.service.IDanWorkflowReimService;
import org.activiti.api.model.shared.model.VariableInstance;
/**
 * 报销申请Service业务层处理
 * 
 * @author wuxiao
 * @date 2022-09-27
 */
@Service
public class DanWorkflowReimServiceImpl implements IDanWorkflowReimService 
{
    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private DanWorkflowReimMapper danWorkflowReimMapper;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    /**
     * 查询报销申请
     * 
     * @param reimId 报销申请主键
     * @return 报销申请
     */
    @Override
    public DanWorkflowReim selectDanWorkflowReimByReimId(String reimId)
    {
        return danWorkflowReimMapper.selectDanWorkflowReimByReimId(reimId);
    }

    /**
     * 查询报销申请列表
     * 
     * @param danWorkflowReim 报销申请
     * @return 报销申请
     */
    @Override
    public List<DanWorkflowReim> selectDanWorkflowReimList(DanWorkflowReim danWorkflowReim)
    {
        List<DanWorkflowReim> workflowLeaves =  danWorkflowReimMapper.selectDanWorkflowReimList(danWorkflowReim);
   /**用于显示审批当前走到哪里了 对应状态栏 **/
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
     * 新增报销申请
     * 
     * @param danWorkflowReim 报销申请
     * @return 结果
     */
    @Transactional
    @Override
    public int insertDanWorkflowReim(DanWorkflowReim danWorkflowReim)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser sysUser = loginUser.getSysUser();
        securityUtil.logInAs(sysUser.getUserName());
        String id = UuidUtil.getShortUuid();
        danWorkflowReim.setCreateTime(DateUtils.getNowDate());
        //guanyu,zhaoyun
        String join = StringUtils.join(sysUserService.selectUserNameByPostCodeAndDeptId("se", sysUser.getDeptId()), ",");
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("reim_at10_10")
                .withName(sysUser.getNickName()+"的报销申请")
                .withBusinessKey(id)
                .withVariable("deptLeader",join)
                .build());
        danWorkflowReim.setReimId(id);
        danWorkflowReim.setInstanceId(processInstance.getId());
        danWorkflowReim.setCreateName(sysUser.getNickName());
        danWorkflowReim.setCreateBy(SecurityUtils.getUsername());
        danWorkflowReim.setCreateTime(DateUtils.getNowDate());
        danWorkflowReim.setStatus(0);
        danWorkflowReim.setCreateTime(DateUtils.getNowDate());

        List<DanWorkflowReimgoods> danWorkflowReimgoods = danWorkflowReim.getDanWorkflowReimgoodsList();
        BigDecimal amount = new BigDecimal(0);
        BigDecimal balance = new BigDecimal(0);
        BigDecimal anleihen = new BigDecimal(0);
        for(DanWorkflowReimgoods reimgood : danWorkflowReimgoods){
            amount = amount.add(reimgood.getMoney());
        }
        if(danWorkflowReim.getAnleihen()==null){
            danWorkflowReim.setAnleihen(anleihen);
        }
        balance =amount.subtract(danWorkflowReim.getAnleihen());

        danWorkflowReim.setAmount(amount);
        danWorkflowReim.setAnleihen(danWorkflowReim.getAnleihen());
        danWorkflowReim.setBalance(balance);

        if(danWorkflowReim.getStatus()==null){
            danWorkflowReim.setStatus(1);
        }
        int rows = danWorkflowReimMapper.insertDanWorkflowReim(danWorkflowReim);
        insertDanWorkflowReimgoods(danWorkflowReim);
        return rows;
    }

    /**
     * 修改报销申请
     * 
     * @param danWorkflowReim 报销申请
     * @return 结果
     */
    @Transactional
    @Override
    public int updateDanWorkflowReim(DanWorkflowReim danWorkflowReim)
    {
        danWorkflowReim.setUpdateTime(DateUtils.getNowDate());
        danWorkflowReimMapper.deleteDanWorkflowReimgoodsByReimId(danWorkflowReim.getReimId());

        List<DanWorkflowReimgoods> danWorkflowReimgoods = danWorkflowReim.getDanWorkflowReimgoodsList();
        BigDecimal amount = new BigDecimal(0);
        BigDecimal balance = new BigDecimal(0);
        BigDecimal anleihen = new BigDecimal(0);
        for(DanWorkflowReimgoods reimgood : danWorkflowReimgoods){
            amount = amount.add(reimgood.getMoney());
        }
        if(danWorkflowReim.getAnleihen()==null){
            danWorkflowReim.setAnleihen(anleihen);
        }
        balance =amount.subtract(danWorkflowReim.getAnleihen());

        danWorkflowReim.setAmount(amount);
        danWorkflowReim.setAnleihen(danWorkflowReim.getAnleihen());
        danWorkflowReim.setBalance(balance);
        insertDanWorkflowReimgoods(danWorkflowReim);

        /**
         * 给流程实例加入变量 这里附件的地址
         * **/
        if(danWorkflowReim.getAttachmentLink()!=null){
            LoginUser loginUser = SecurityUtils.getLoginUser();
            SysUser sysUser = loginUser.getSysUser();
            securityUtil.logInAs(sysUser.getUserName());
            List<VariableInstance> list = processRuntime.variables(ProcessPayloadBuilder
                    .variables()
                    .withProcessInstanceId(danWorkflowReim.getInstanceId())
                    .build()
            );
            boolean attachmentLinkflag = false;
            for(VariableInstance vi : list){
                System.out.println("getProcessInstanceId：" + vi.getProcessInstanceId());
                if("attachmentLink".equals(vi.getName())){
                    attachmentLinkflag=true;
                }
            }
            if(!attachmentLinkflag){
                runtimeService.setVariable(danWorkflowReim.getInstanceId(),"attachmentLink",danWorkflowReim.getAttachmentLink());
            }
        }

        return danWorkflowReimMapper.updateDanWorkflowReim(danWorkflowReim);
    }

    /**
     * 批量删除报销申请
     * 
     * @param reimIds 需要删除的报销申请主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteDanWorkflowReimByReimIds(String[] reimIds)
    {
        danWorkflowReimMapper.deleteDanWorkflowReimgoodsByReimIds(reimIds);
        return danWorkflowReimMapper.deleteDanWorkflowReimByReimIds(reimIds);
    }

    /**
     * 删除报销申请信息
     * 
     * @param reimId 报销申请主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteDanWorkflowReimByReimId(String reimId)
    {
        danWorkflowReimMapper.deleteDanWorkflowReimgoodsByReimId(reimId);
        return danWorkflowReimMapper.deleteDanWorkflowReimByReimId(reimId);
    }

    /**
     * 新增报销条目信息
     * 
     * @param danWorkflowReim 报销申请对象
     */
    public void insertDanWorkflowReimgoods(DanWorkflowReim danWorkflowReim)
    {
        List<DanWorkflowReimgoods> danWorkflowReimgoodsList = danWorkflowReim.getDanWorkflowReimgoodsList();
        String reimId = danWorkflowReim.getReimId();
        if (StringUtils.isNotNull(danWorkflowReimgoodsList))
        {
            List<DanWorkflowReimgoods> list = new ArrayList<DanWorkflowReimgoods>();
            for (DanWorkflowReimgoods danWorkflowReimgoods : danWorkflowReimgoodsList)
            {
                danWorkflowReimgoods.setReimId(reimId);
                list.add(danWorkflowReimgoods);
            }
            if (list.size() > 0)
            {
                danWorkflowReimMapper.batchDanWorkflowReimgoods(list);
            }
        }
    }
    @Override
    public HashMap searchReimById(String id) {
        DanWorkflowReim danWorkflowReim = danWorkflowReimMapper.selectDanWorkflowReimByReimId(id);
        HashMap map= JSONUtil.parse(danWorkflowReim).toBean(HashMap.class);
        String instanceId= MapUtil.getStr(map,"instanceId");
        QrConfig qrConfig=new QrConfig();
        qrConfig.setWidth(70);
        qrConfig.setHeight(70);
        qrConfig.setMargin(2);
        String qrCodeBase64= QrCodeUtil.generateAsBase64(instanceId,qrConfig,"jpg");
        map.put("date",DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss",danWorkflowReim.getCreateTime()));
        map.put("qrCodeBase64",qrCodeBase64);
        return map;
    }

}
