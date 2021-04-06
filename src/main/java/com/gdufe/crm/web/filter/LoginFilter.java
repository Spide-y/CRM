package com.gdufe.crm.web.filter;

import com.gdufe.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        //System.out.println("判断有没登录");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String path = req.getServletPath();
        if ("/login.do".equals(path)||"/login.jsp".equals(path)){
            chain.doFilter(servletRequest,servletResponse);
        }else {
            HttpSession session = req.getSession();
            User user = (User)session.getAttribute("user");

            if (user!=null){
                chain.doFilter(servletRequest,servletResponse);
            }else {
                resp.sendRedirect(req.getContextPath()+"/login.jsp");
            }
        }

    }
}
