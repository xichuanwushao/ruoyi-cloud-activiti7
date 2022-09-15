package com.ruoyi.danny.service;

import java.util.Set;

public interface ISysPermissionService
{


    /**
     * 获取菜单数据权限
     * 
     * @param userId 用户Id
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(Long userId);
}
