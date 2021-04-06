package com.gdufe.crm.settings.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdufe.crm.settings.domain.User;
import com.gdufe.crm.settings.service.UserService;
import com.gdufe.crm.utils.DateTimeUtil;
import com.gdufe.crm.utils.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Resource
    private UserService service;

    @RequestMapping("/login.do")
    @ResponseBody
    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String loginAct = req.getParameter("loginAct");
        String loginPwd = req.getParameter("loginPwd");
        loginPwd = MD5Util.getMD5(loginPwd);
        //接收ip地址
        String ip = req.getRemoteAddr();
        System.out.println("ip======"+ip+"   "+loginAct+"   "+loginPwd);
        User user = new User();
        user.setLoginAct(loginAct);
        user.setLoginPwd(loginPwd);
        User userList = service.selectUsers(user);
        System.out.println(userList);
        Map<String ,String> msg = new HashMap<String, String>();
        msg.put("success","false");
        msg.put("msg","账号密码错误");
        String json = "";
        if (userList!=null){
            msg.put("success","true");
            if (userList.getExpireTime().compareTo(DateTimeUtil.getSysTime())<0){
                msg.put("success","false");
                msg.put("msg","账号已失效");
            }
            if ("0".equals(userList.getLockState())){
                msg.put("success","false");
                msg.put("msg","账号已被锁定");
            }
            req.getSession().setAttribute("user",userList);
        }
        ObjectMapper om = new ObjectMapper();
        json = om.writeValueAsString(msg);
        System.out.println(json);
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter pw = resp.getWriter();
        pw.println(json);
        pw.flush();
        pw.close();
    }


}