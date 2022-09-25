package com.ruoyi.danny.service;


import com.ruoyi.danny.domain.dto.HistoryDataDTO;

import java.util.List;

/**
 *
 * 创建日期:2020/11/3-10:19
 * 版本   开发者     日期
 * 1.0    Danny    2020/11/3
 */
public interface IFormHistoryDataService {

    public List<HistoryDataDTO> historyDataShow(String instanceId);
}
