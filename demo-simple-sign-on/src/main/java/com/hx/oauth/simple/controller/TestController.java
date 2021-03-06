package com.hx.oauth.simple.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "get/all")
    public String getAll(){
        return "This is all";
    }

    @PreAuthorize("hasRole('aadmin')")
    @GetMapping(value = "find/all")
    public String find(){
        return "This is all";
    }

    @GetMapping(value = "user")
    public Principal user(Principal principal) {
        System.out.println("调用user接口获取用户信息：" + principal);
        return principal;
    }
}
