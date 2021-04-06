package com.gdufe.crm.settings.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdufe.crm.settings.service.UserService;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {

    @Resource
    private UserService service;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        String path = req.getServletPath();
        System.out.println("=================="+path);
        if ("/login.do".equals(path)||req.getSession().getAttribute("user")!=null){
            return true;
        }else {
            return false;
        }
    }

}
