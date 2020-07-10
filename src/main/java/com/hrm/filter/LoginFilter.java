package com.hrm.filter;

import com.hrm.commons.beans.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    /*不进行过滤的页面、处理器方法或其它静态资源*/

    String[] IG_URI = {"/index.jsp","loginForm.jsp","/login","/",".css",".js",".jpg"};
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        /*强制类型转换*/
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        /*获取本次请求的uri*/
        String requestURI = request.getRequestURI();
        /*循环判断本次请求的URI是否为数组中定义的字符串*/
        for(String s:IG_URI){
            /*如果本次请求是不进行过滤的请求*/
            if (requestURI.endsWith(s)){
                /*直接放行*/
                chain.doFilter(request,response);
                return;
            }
        }

        /*从session中获取用户登陆的信息*/
        User login_user = (User) request.getSession().getAttribute("login_user");
        /*如果用户已经登陆*/
        if (login_user != null){
            /*直接放行访问*/
            chain.doFilter(request,response);
        }else {
            /*用户未登陆*/
            request.setAttribute("login_error","您还未登陆,请登陆后访问");
            request.getRequestDispatcher("/index.jsp").forward(request,response);
        }
    }

    @Override
    public void destroy() {

    }
}
