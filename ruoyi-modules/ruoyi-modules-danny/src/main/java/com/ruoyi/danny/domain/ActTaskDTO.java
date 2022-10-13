package com.ruoyi.danny.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.activiti.api.task.model.Task;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.Date;

public class ActTaskDTO
        extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    private String instanceName;
    private String instanceId;
    private String definitionKey;
    private String businessKey;
    private String attachmentLink;
    private String assignee;

    public ActTaskDTO() {
    }

    public ActTaskDTO(Task task, ProcessInstance processInstance) {
        this.id = task.getId();
        this.name = task.getName();
        this.status = task.getStatus().toString();
        this.createdDate = task.getCreatedDate();
        this.instanceName = processInstance.getName();
        this.instanceId = processInstance.getProcessInstanceId();
        this.definitionKey=processInstance.getProcessDefinitionKey();
        this.businessKey=processInstance.getBusinessKey();
    }


    public String getAttachmentLink() {
        return attachmentLink;
    }

    public void setAttachmentLink(String attachmentLink) {
        this.attachmentLink = attachmentLink;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getDefinitionKey() {
        return definitionKey;
    }

    public void setDefinitionKey(String definitionKey) {
        this.definitionKey = definitionKey;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
}
