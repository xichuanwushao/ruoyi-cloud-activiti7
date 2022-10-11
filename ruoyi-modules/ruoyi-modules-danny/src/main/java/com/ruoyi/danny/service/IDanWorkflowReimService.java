package com.ruoyi.danny.service;

import java.util.HashMap;
import java.util.List;
import com.ruoyi.danny.domain.DanWorkflowReim;

/**
 * 报销申请Service接口
 * 
 * @author wuxiao
 * @date 2022-09-27
 */
public interface IDanWorkflowReimService 
{
    /**
     * 查询报销申请
     * 
     * @param reimId 报销申请主键
     * @return 报销申请
     */
    public DanWorkflowReim selectDanWorkflowReimByReimId(String reimId);

    /**
     * 查询报销申请列表
     * 
     * @param danWorkflowReim 报销申请
     * @return 报销申请集合
     */
    public List<DanWorkflowReim> selectDanWorkflowReimList(DanWorkflowReim danWorkflowReim);

    /**
     * 新增报销申请
     * 
     * @param danWorkflowReim 报销申请
     * @return 结果
     */
    public int insertDanWorkflowReim(DanWorkflowReim danWorkflowReim);

    /**
     * 修改报销申请
     * 
     * @param danWorkflowReim 报销申请
     * @return 结果
     */
    public int updateDanWorkflowReim(DanWorkflowReim danWorkflowReim);

    /**
     * 批量删除报销申请
     * 
     * @param reimIds 需要删除的报销申请主键集合
     * @return 结果
     */
    public int deleteDanWorkflowReimByReimIds(String[] reimIds);

    /**
     * 删除报销申请信息
     * 
     * @param reimId 报销申请主键
     * @return 结果
     */
    public int deleteDanWorkflowReimByReimId(String reimId);

    public HashMap searchReimById(String id);
}
