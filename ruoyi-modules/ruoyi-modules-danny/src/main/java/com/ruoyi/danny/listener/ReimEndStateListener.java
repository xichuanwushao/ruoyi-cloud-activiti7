package com.ruoyi.danny.listener;

import com.ruoyi.common.core.utils.SpringUtils;
import com.ruoyi.danny.domain.DanWorkflowReim;
import com.ruoyi.danny.domain.WorkflowLeave;
import com.ruoyi.danny.service.IDanWorkflowReimService;
import com.ruoyi.danny.service.IWorkflowLeaveService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;


public class ReimEndStateListener implements ExecutionListener {
    private Expression status;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        DanWorkflowReim workflowLeave = new DanWorkflowReim();
        workflowLeave.setReimId(delegateExecution.getProcessInstanceBusinessKey());
        workflowLeave.setStatus(Integer.parseInt(status.getValue(delegateExecution).toString()));
        SpringUtils.getBean(IDanWorkflowReimService.class).updateDanWorkflowReim(workflowLeave);
    }
}
