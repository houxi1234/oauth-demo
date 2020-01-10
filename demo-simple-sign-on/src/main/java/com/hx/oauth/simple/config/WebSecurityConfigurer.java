package com.hx.oauth.simple.config;

import com.hx.oauth.simple.service.impl.MyUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author houxi
 * @version 1.0
 * @date 2020/1/9 17:14
 */
@Configurable
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsManager userDetailsManager;

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/assets/**", "/css/**", "/images/**");
//    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsManager).passwordEncoder(passwordEncoder());
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests().antMatchers("/get/**").authenticated()
                .and()
                .formLogin().loginPage("/login.html").loginProcessingUrl("/login").defaultSuccessUrl("/find/all",false).permitAll()
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login.html").permitAll()
                .and().httpBasic()
                .and().csrf().disable();
    }
    //http
    //			.authorizeRequests()//配置权限
    //				.anyRequest().authenticated()//任意请求需要登录
    //				.and()
    //			.formLogin()//开启formLogin默认配置
    //				.loginPage("/login/auth").permitAll()//请求时未登录跳转接口
    //		                .failureUrl("/login/fail")//用户密码错误跳转接口
    //				.defaultSuccessUrl("/login/success",true)//登录成功跳转接口
    //				.loginProcessingUrl("/login")//post登录接口，登录验证由系统实现
    //				.usernameParameter("username")	//要认证的用户参数名，默认username
    //				.passwordParameter("password")	//要认证的密码参数名，默认password
    //				.and()
    //			.logout()//配置注销
    //				.logoutUrl("/logout")//注销接口
    //				.logoutSuccessUrl("/login/logout").permitAll()//注销成功跳转接口
    //				.deleteCookies("myCookie") //删除自定义的cookie
    //				.and()
    //			.csrf().disable();

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
