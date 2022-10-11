package com.ruoyi.danny.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 报销条目对象 dan_workflow_reimgoods
 * 
 * @author wuxiao
 * @date 2022-09-27
 */
public class DanWorkflowReimgoods extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 报销条目id */
    private Long reimgoodsId;

    /** 报销申请id */
    @Excel(name = "报销申请id")
    private String reimId;

    /** 条目标题 */
    @Excel(name = "条目标题")
    private String title;

    /** 条目类型 */
    @Excel(name = "条目类型")
    private Integer type;

    /** 条目金额 */
    @Excel(name = "条目金额")
    private BigDecimal money;

    /** 条目描述 */
    @Excel(name = "条目描述")
    private String desc;

    public void setReimgoodsId(Long reimgoodsId) 
    {
        this.reimgoodsId = reimgoodsId;
    }

    public Long getReimgoodsId() 
    {
        return reimgoodsId;
    }

    public String getReimId() {
        return reimId;
    }

    public void setReimId(String reimId) {
        this.reimId = reimId;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setType(Integer type) 
    {
        this.type = type;
    }

    public Integer getType() 
    {
        return type;
    }
    public void setMoney(BigDecimal money) 
    {
        this.money = money;
    }

    public BigDecimal getMoney() 
    {
        return money;
    }
    public void setDesc(String desc) 
    {
        this.desc = desc;
    }

    public String getDesc() 
    {
        return desc;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("reimgoodsId", getReimgoodsId())
            .append("reimId", getReimId())
            .append("title", getTitle())
            .append("type", getType())
            .append("money", getMoney())
            .append("desc", getDesc())
            .toString();
    }
}
