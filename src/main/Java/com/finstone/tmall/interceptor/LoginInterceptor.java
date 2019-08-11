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

    private static String[] noNeedAuthPage = { //不需要拦截的请求 - ForeController、LogController中的所有
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

    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * 如果返回true
     *    执行下一个拦截器,直到所有的拦截器都执行完毕
     *    再执行被拦截的Controller
     *    然后进入拦截器链,
     *    从最后一个拦截器往回执行所有的postHandle()
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
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
                    //request.getRequestDispatcher("loginPage").forward(request, response);
                    response.sendRedirect("loginPage");
                    return false;
                }
                return true;
            }
        }
        return true;
    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     * 可在modelAndView中加入数据
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //System.out.println("postHandle......");
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //System.out.println("afterCompletion......");
    }
}
