package com.ruoyi.danny.mapper;

import java.util.List;
import com.ruoyi.danny.domain.DanWorkflowReim;
import com.ruoyi.danny.domain.DanWorkflowReimgoods;

/**
 * 报销申请Mapper接口
 * 
 * @author wuxiao
 * @date 2022-09-27
 */
public interface DanWorkflowReimMapper 
{
    /**
     * 查询报销申请
     * 
     * @param reimId 报销申请主键
     * @return 报销申请
     */
    public DanWorkflowReim selectDanWorkflowReimByReimId(Long reimId);

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
     * 删除报销申请
     * 
     * @param reimId 报销申请主键
     * @return 结果
     */
    public int deleteDanWorkflowReimByReimId(Long reimId);

    /**
     * 批量删除报销申请
     * 
     * @param reimIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDanWorkflowReimByReimIds(Long[] reimIds);

    /**
     * 批量删除报销条目
     * 
     * @param reimIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDanWorkflowReimgoodsByReimIds(Long[] reimIds);
    
    /**
     * 批量新增报销条目
     * 
     * @param danWorkflowReimgoodsList 报销条目列表
     * @return 结果
     */
    public int batchDanWorkflowReimgoods(List<DanWorkflowReimgoods> danWorkflowReimgoodsList);
    

    /**
     * 通过报销申请主键删除报销条目信息
     * 
     * @param reimId 报销申请ID
     * @return 结果
     */
    public int deleteDanWorkflowReimgoodsByReimId(Long reimId);
}
