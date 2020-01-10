//package com.hx.oauth.simple.config;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
//import org.springframework.security.web.savedrequest.RequestCache;
//import org.springframework.security.web.savedrequest.SavedRequest;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @author houxi
// * @version 1.0
// * @date 2020/1/10 14:16
// */
//public class DefinedAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        response.setContentType("application/json;charset=utf-8");
//        RequestCache cache = new HttpSessionRequestCache();
//        SavedRequest savedRequest = cache.getRequest(request, response);
//        String url = savedRequest.getRedirectUrl();
//        response.sendRedirect(url);
//    }
//}
