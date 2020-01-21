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
    private static Map<String, User> userDetailsDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SysUserMapper userMapper;

    static {
        userDetailsDao = new HashMap<>();
        User user = new User("user", "{bcrypt}$2a$10$KefBTiAKaATWTJWCfhMUHOCjlxW1TZprKT4DpTPH9Qvyrg58W2SPy", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ANONYMOUS"));
        userDetailsDao.put(user.getUsername(), user);
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser byUserName = userMapper.findByUserName(username);
        User user = userDetailsDao.get(username);
        if (null == user) {
            log.warn("用户{}不存在", username);
            throw new UsernameNotFoundException(username);
        }
        User myUser = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getAuthorities());
        log.info("登录成功！用户: {}, 密码: {}", myUser, myUser.getPassword());
        log.info(user.getPassword());
        return myUser;
    }
}
