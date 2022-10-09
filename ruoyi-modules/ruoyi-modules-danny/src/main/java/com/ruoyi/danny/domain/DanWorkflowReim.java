package com.ruoyi.danny.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 报销申请对象 dan_workflow_reim
 * 
 * @author wuxiao
 * @date 2022-09-27
 */
public class DanWorkflowReim extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 报销申请id */
    private Long reimId;

    /** 总金额 */
    @Excel(name = "总金额")
    private BigDecimal amount;

    /** 借款 */
    @Excel(name = "借款")
    private BigDecimal anleihen;

    /** 差额 */
    @Excel(name = "差额")
    private BigDecimal balance;

    /** 类型：1普通报销，2差旅报销 */
    @Excel(name = "类型：1普通报销，2差旅报销")
    private Integer type;

    /** 状态：1审批中，2已拒绝，3审批通过，4.已归档 */
    @Excel(name = "状态：1审批中，2已拒绝，3审批通过，4.已归档")
    private Integer status;

    /** 报销标题 */
    @Excel(name = "报销标题")
    private String title;

    /** 报销原因 */
    @Excel(name = "报销原因")
    private String reason;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date leaveStartTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date leaveEndTime;

    /** 附件 */
    @Excel(name = "附件")
    private String attachmentLink;

    /** 流程实例id */
    @Excel(name = "流程实例id")
    private String instanceId;

    /** 创建者名称 */
    @Excel(name = "创建者名称")
    private String createName;

    /** 报销条目信息 */
    private List<DanWorkflowReimgoods> danWorkflowReimgoodsList;

    /** bpmn流程图片地址 */
    private String bpmnUrl;

    public String getBpmnUrl() {
        return bpmnUrl;
    }

    public void setBpmnUrl(String bpmnUrl) {
        this.bpmnUrl = bpmnUrl;
    }

    public void setReimId(Long reimId)
    {
        this.reimId = reimId;
    }

    public Long getReimId() 
    {
        return reimId;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setAnleihen(BigDecimal anleihen) 
    {
        this.anleihen = anleihen;
    }

    public BigDecimal getAnleihen() 
    {
        return anleihen;
    }
    public void setBalance(BigDecimal balance) 
    {
        this.balance = balance;
    }

    public BigDecimal getBalance() 
    {
        return balance;
    }
    public void setType(Integer type) 
    {
        this.type = type;
    }

    public Integer getType() 
    {
        return type;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setReason(String reason) 
    {
        this.reason = reason;
    }

    public String getReason() 
    {
        return reason;
    }
    public void setLeaveStartTime(Date leaveStartTime) 
    {
        this.leaveStartTime = leaveStartTime;
    }

    public Date getLeaveStartTime() 
    {
        return leaveStartTime;
    }
    public void setLeaveEndTime(Date leaveEndTime) 
    {
        this.leaveEndTime = leaveEndTime;
    }

    public Date getLeaveEndTime() 
    {
        return leaveEndTime;
    }
    public void setAttachmentLink(String attachmentLink) 
    {
        this.attachmentLink = attachmentLink;
    }

    public String getAttachmentLink() 
    {
        return attachmentLink;
    }
    public void setInstanceId(String instanceId) 
    {
        this.instanceId = instanceId;
    }

    public String getInstanceId() 
    {
        return instanceId;
    }
    public void setCreateName(String createName) 
    {
        this.createName = createName;
    }

    public String getCreateName() 
    {
        return createName;
    }

    public List<DanWorkflowReimgoods> getDanWorkflowReimgoodsList()
    {
        return danWorkflowReimgoodsList;
    }

    public void setDanWorkflowReimgoodsList(List<DanWorkflowReimgoods> danWorkflowReimgoodsList)
    {
        this.danWorkflowReimgoodsList = danWorkflowReimgoodsList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("reimId", getReimId())
            .append("amount", getAmount())
            .append("anleihen", getAnleihen())
            .append("balance", getBalance())
            .append("type", getType())
            .append("status", getStatus())
            .append("title", getTitle())
            .append("reason", getReason())
            .append("leaveStartTime", getLeaveStartTime())
            .append("leaveEndTime", getLeaveEndTime())
            .append("attachmentLink", getAttachmentLink())
            .append("instanceId", getInstanceId())
            .append("createName", getCreateName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("danWorkflowReimgoodsList", getDanWorkflowReimgoodsList())
            .toString();
    }
}
