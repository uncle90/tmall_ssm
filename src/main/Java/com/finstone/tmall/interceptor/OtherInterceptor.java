package com.finstone.tmall.interceptor;

import com.finstone.tmall.entity.Category;
import com.finstone.tmall.entity.OrderItem;
import com.finstone.tmall.entity.User;
import com.finstone.tmall.service.CategoryService;
import com.finstone.tmall.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class OtherInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    CategoryService categoryService;
    @Autowired
    OrderItemService orderItemService;


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
        return true;
    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     * 可在modelAndView中加入数据
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //System.out.println("postHandle2222......");

        //获取商品分类集合，放在搜索框下
        List<Category> cs = categoryService.list();
        request.getSession().setAttribute("cs",cs);

        //获取当前应用名称contextPath，放在回到首页的超链接中
        String contextPath = request.getSession().getServletContext().getContextPath();
        request.getSession().setAttribute("contextPath",contextPath);

        //获取用户购物车中的商品数量 cartTotalItemNumber
        User user = (User) request.getSession().getAttribute("user");
        if(null==user){
            request.getSession().removeAttribute("cartTotalItemNumber");
        }else{
            List<OrderItem> ois = orderItemService.listByUser(user.getId());
            int cartTotalItemNumber = 0;
            for(OrderItem o: ois){
                cartTotalItemNumber += o.getNumber();
            }
            request.getSession().setAttribute("cartTotalItemNumber",cartTotalItemNumber);
        }

    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //System.out.println("afterCompletion2222......");
    }
}
