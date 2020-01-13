package com.hx.oauth.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@SpringBootApplication
@EnableOAuth2Sso
public class Demo001Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo001Application.class, args);
    }

}
