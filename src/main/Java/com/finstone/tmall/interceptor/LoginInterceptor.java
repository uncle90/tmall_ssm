package com.finstone.tmall.interceptor;

import com.finstone.tmall.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    public static String[] noNeedAuthPage = { //不需要拦截的请求 - ForeController、LogController中的所有
            "forehome",
            "foreproduct",
            "forecategory",
            "foresearch",
            "loginPage",
            "forelogin",
            "forecheckLogin",
            "foreloginAjax",
            "registerPage",
            "foreregister",
            "registerSuccess",
            "forelogout"
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取请求中的方法
        HttpSession session = request.getSession();
        String contextPath = request.getContextPath();
        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri, contextPath);

        //判断是否需要登录
        if(uri.startsWith("/fore")){
            String method = StringUtils.substringAfterLast(uri, "/");
            if(!Arrays.asList(noNeedAuthPage).contains(method)){
                //判断是否已登录
                User user = (User) session.getAttribute("user");
                if(null==user){
                    response.sendRedirect("loginPage");
                    return false;
                }
                return true;
            }
        }
        return true;
    }

}
