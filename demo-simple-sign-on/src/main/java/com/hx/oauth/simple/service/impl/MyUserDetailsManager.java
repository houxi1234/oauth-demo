package com.hx.oauth.simple.service.impl;

import com.hx.oauth.simple.mapper.SysUserMapper;
import com.hx.oauth.simple.po.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author houxi
 * @version 1.0
 * @date 2020/1/7 14:59
 */
@Component
public class MyUserDetailsManager implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(MyUserDetailsManager.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUserName = userMapper.findByUserName(username);
        if (null == sysUserName) {
            log.warn("用户{}不存在", username);
            throw new UsernameNotFoundException(username);
        }
        User myUser = new User(sysUserName.getUserName(), passwordEncoder.encode(sysUserName.getPassword()), new ArrayList<>());
        return myUser;
    }
}
