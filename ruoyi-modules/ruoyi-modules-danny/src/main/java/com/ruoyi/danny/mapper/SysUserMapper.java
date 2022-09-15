package com.ruoyi.danny.mapper;


import com.ruoyi.danny.domain.SysUser;

import java.util.List;

/**
 * 用户表 数据层
 * 
 * @author ruoyi
 */
public interface SysUserMapper
{

//    public List<String> selectUserNameByPostCodeAndDeptId(@Param("postCode")String postCode,@Param("deptId") String deptId);
    public List<String>selectUserNameByPostCodeAndDeptId(String postCode,Long deptId);
    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);
}
