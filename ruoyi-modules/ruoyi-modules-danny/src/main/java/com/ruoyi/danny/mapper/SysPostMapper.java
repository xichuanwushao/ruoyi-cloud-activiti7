package com.ruoyi.danny.mapper;


import java.util.Set;

/**
 * 岗位信息 数据层
 * 
 * @author ruoyi
 */
public interface SysPostMapper
{


    /**
     * 根据用户ID获取岗位选择框列表
     * 
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    public Set<String> selectPostCodeByUserId(Long userId);


}
