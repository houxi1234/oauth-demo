package com.hx.oauth.resource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author houxi
 * @version 1.0
 * @date 2020/1/7 10:21
 */
@RestController
public class TestController {

    @GetMapping(value = "get/{id}")
    public String get(@PathVariable("id") String id){
        return "This is " + id;
    }

    @GetMapping(value = "user")
    public Principal user(Principal principal) {
        System.out.println("调用user接口获取用户信息：" + principal);
        return principal;
    }
}
