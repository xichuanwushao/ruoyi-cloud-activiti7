package com.ruoyi.danny.service;

import com.github.pagehelper.Page;
import com.ruoyi.common.core.web.page.PageDomain;
import com.ruoyi.danny.domain.ActTaskDTO;
import com.ruoyi.danny.domain.dto.ActWorkflowFormDataDTO;

import java.util.List;

public interface IActTaskService {
    public Page<ActTaskDTO> selectProcessDefinitionList(PageDomain pageDomain);

    public List<String>formDataShow(String taskID);

    int formDataSave(String taskID, List<ActWorkflowFormDataDTO> formData);
}
