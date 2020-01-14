package com.hx.oauth.simple.config;

import com.hx.oauth.simple.service.impl.MyUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
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
@Order(3)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .requestMatchers()//使HttpSecurity接收以"/login/","/oauth/"开头请求。
                .antMatchers("/oauth/**", "/login/**", "/logout/**","/error")
                .and().authorizeRequests().antMatchers("/get/**").authenticated()
                .and()
                .formLogin().loginPage("/login.html").loginProcessingUrl("/login").defaultSuccessUrl("/find/all",false).permitAll()
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login.html").permitAll()

                .and().httpBasic()
                .and().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
