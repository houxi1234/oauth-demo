package com.hx.oauth.resource.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    private static final Logger log = LoggerFactory.getLogger(TestController.class);


    @GetMapping(value = "api/{id}")
    public String get(@PathVariable("id") String id){
        return "This is " + id;
    }



    @GetMapping("/user")
    public Authentication getUser(Authentication authentication) {
        log.info("resource: user {}", authentication);
        return authentication;
    }

}
