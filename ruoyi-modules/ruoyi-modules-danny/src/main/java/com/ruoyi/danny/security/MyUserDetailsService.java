package com.ruoyi.danny.security;


import com.ruoyi.common.core.enums.UserStatus;
import com.ruoyi.common.core.exception.base.BaseException;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.danny.domain.LoginUser;
import com.ruoyi.danny.domain.SysUser;
import com.ruoyi.danny.service.ISysPermissionService;
import com.ruoyi.danny.service.ISysPostService;
import com.ruoyi.danny.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class MyUserDetailsService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final Logger log = LoggerFactory.getLogger(MyUserDetailsService.class);
//    @Autowired
//    UserInfoBeanMapper mapper;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        logger.info("登录用户名：" + username);
//        String passWord= passwordEncoder().encode("111");
//        logger.info("密码：" + passWord);
//        return new User(username,
//                passWord,
//                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ACTIVITI_USER"));
//    }
    @Autowired
    private ISysUserService userService;


    @Autowired
    private ISysPostService sysPostService;



    @Autowired
    private ISysPermissionService permissionService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserInfoBean userInfoBean = mapper.selectByUsername(username);
//        if (userInfoBean == null) {
//            throw new UsernameNotFoundException("数据库中无此用户！");
//        }
//        return userInfoBean;
        SysUser user = userService.selectUserByUserName(username);
        if (StringUtils.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", username);
            throw new BaseException("对不起，您的账号：" + username + " 已被删除");
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new BaseException("对不起，您的账号：" + username + " 已停用");
        }

        return createLoginUser(user);
    }
    public UserDetails createLoginUser(SysUser user) {
        Set<String> postCode = sysPostService.selectPostCodeByUserId(user.getUserId());
        postCode = postCode.parallelStream().map( s ->  "GROUP_" + s).collect(Collectors.toSet());
        postCode.add("ROLE_ACTIVITI_USER");
        List<SimpleGrantedAuthority> collect = postCode.stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList());
        return new LoginUser(user, permissionService.getMenuPermission(user.getUserId()), collect);
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}