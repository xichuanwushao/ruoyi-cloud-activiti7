package com.ruoyi.danny.service;



import com.ruoyi.danny.domain.SysUser;

import java.util.List;

/**
 * 用户 业务层
 * 
 * @author ruoyi
 */
public interface ISysUserService
{


    public List<String> selectUserNameByPostCodeAndDeptId(String postCode,Long deptId);


    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);
}
