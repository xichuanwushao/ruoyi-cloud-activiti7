package com.ruoyi.danny.service.impl;

import com.ruoyi.danny.domain.SysUser;
import com.ruoyi.danny.service.ISysMenuService;
import com.ruoyi.danny.service.ISysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SysPermissionServiceImpl implements ISysPermissionService
{

    @Autowired
    private ISysMenuService menuService;



    /**
     * 获取菜单数据权限
     * 
     * @param userId 用户Id
     * @return 菜单权限信息
     */
    @Override
    public Set<String> getMenuPermission(Long userId)
    {
        Set<String> perms = new HashSet<String>();
        // 管理员拥有所有权限
        if (SysUser.isAdmin(userId))
        {
            perms.add("*:*:*");
        }
        else
        {
            perms.addAll(menuService.selectMenuPermsByUserId(userId));
        }
        return perms;
    }
}
