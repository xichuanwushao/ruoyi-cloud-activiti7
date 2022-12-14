package com.ruoyi.danny.listener;

import com.ruoyi.common.core.utils.SpringUtils;
import com.ruoyi.danny.domain.WorkflowLeave;
import com.ruoyi.danny.service.IWorkflowLeaveService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;


public class LeaveEndStateListener implements ExecutionListener {
    private Expression state;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        WorkflowLeave workflowLeave = new WorkflowLeave();
        workflowLeave.setId(delegateExecution.getProcessInstanceBusinessKey());
        workflowLeave.setState(state.getValue(delegateExecution).toString());
        SpringUtils.getBean(IWorkflowLeaveService.class).updateWorkflowLeave(workflowLeave);
    }
}
