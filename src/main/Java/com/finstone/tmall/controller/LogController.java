package com.finstone.tmall.controller;

import com.finstone.tmall.entity.User;
import com.finstone.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class LogController {

    @Autowired
    UserService userService;

    /**
     * 登录页
     * @return
     */
    @RequestMapping("loginPage")
    public String loginPage(){
        return "fore/login";
    }

    /**
     * 登录，成功后前往首页
     * @param model
     * @param session
     * @param name
     * @param password
     * @return
     */
    @RequestMapping("forelogin")
    public String login(Model model, HttpSession session,
                        @RequestParam("name") String name,
                        @RequestParam("password") String password){
        //特殊字符转义
        name = HtmlUtils.htmlEscape(name);
        //校验账号
        User user = userService.get(name,password);
        if(user == null){
            model.addAttribute("msg","账号密码错误");
            model.addAttribute("user",null);
            return "fore/login";
        }
        model.addAttribute("user",user);
        //添加session
        session.setAttribute("user", user);
        //TODO 查询购物车 查询订单
        return "redirect:forehome";
    }

    /**
     * 检查是否登录
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String forecheckLogin(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            model.addAttribute("msg","账号密码错误");
            model.addAttribute("user",null);
            return "fail";
        }else{
            return "success";
        }
    }

    /**
     * 模态框登录
     * @return
     */
    @RequestMapping("foreloginAjax")
    @ResponseBody
    public String loginAjax(HttpSession session,
                            @RequestParam("name") String name,
                            @RequestParam("password") String password){
        //检查会话，是否已登录
        User usertemp = (User) session.getAttribute("user");
        if(usertemp != null){
            return "success";
        }
        //特殊字符转义
        name = HtmlUtils.htmlEscape(name);
        //校验账号
        User user = userService.get(name,password);
        if(user == null){
            return "fail";
        }
        //添加session
        session.setAttribute("user", user);
        return "success";
    }

    /**
     * 注册页
     * @return
     */
    @RequestMapping("registerPage")
    public String register(){
        return "fore/register";
    }

    /**
     * 注册
     * @param model
     * @param user
     * @return
     */
    @RequestMapping("foreregister")
    public String foreregister(Model model, User user){
        //特殊字符转义
        String name = HtmlUtils.htmlEscape(user.getName());
        user.setName(name);
        //User接收form表单带name的提交
        if(userService.isExist(user.getName())){
            model.addAttribute("msg","用户名已经被使用,不能使用");
            model.addAttribute("user",null); //top.jsp
            return "fore/register";
        }
        //注册
        userService.add(user);
        return "redirect:registerSuccess";
    }

    /**
     * 注册成功页
     * @return
     */
    @RequestMapping("registerSuccess")
    public String registerSuccess(){
        return "fore/registerSuccess";
    }

    /**
     * 退出
     * @param session
     * @return
     */
    @RequestMapping("forelogout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:forehome";
    }
}
