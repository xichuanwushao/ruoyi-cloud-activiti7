package com.ruoyi.danny.service;


import java.util.List;
import java.util.Set;

/**
 * 岗位信息 服务层
 * 
 * @author ruoyi
 */
public interface ISysPostService
{

    /**
     * 根据用户ID获取岗位选择框列表
     * 
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    public Set<String> selectPostCodeByUserId(Long userId);


}
