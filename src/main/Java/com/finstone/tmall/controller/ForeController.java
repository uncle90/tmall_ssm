package com.finstone.tmall.controller;

import com.finstone.tmall.entity.Category;
import com.finstone.tmall.entity.Product;
import com.finstone.tmall.entity.ProductImage;
import com.finstone.tmall.entity.User;
import com.finstone.tmall.service.CategoryService;
import com.finstone.tmall.service.ProductImageService;
import com.finstone.tmall.service.ProductService;
import com.finstone.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("")
public class ForeController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductImageService productImageService;

    /**
     * 首页
     * 一、功能
     * 1. 在横向导航栏上提供4个分类连接
     * 2. 在纵向导航栏上提供全部17个分类连接
     * 3. 当鼠标移动到某一个纵向分类连接的时候，显示这个分类下的推荐产品列表
     * 4. 按照每种分类显示5个商品的方式，显示所有17种分类
     * <p>
     * 二、页面结构
     * <%@include file="../include/fore/header.jsp"%>
     * <%@include file="../include/fore/top.jsp"%>                      导航栏
     * <%@include file="../include/fore/search.jsp"%>                   搜索框
     * <%@include file="../include/fore/home/homePage.jsp"%>            页面主体
     * <%@include file="categoryAndcarousel.jsp"%>                  导航和轮播、分类和产品推荐
     * rightMenu：<a href="forecategory?cid=${c.id}">      导航
     * <%@include file="categoryMenu.jsp" %>               商品分类
     * <%@include file="productsAsideCategorys.jsp" %>     推荐商品（按类别分组）
     * <%@include file="carousel.jsp" %>                   轮播
     * <%@include file="homepageCategoryProducts.jsp"%>             产品列表
     * <%@include file="../include/fore/footer.jsp"%>
     */
    @RequestMapping("forehome")
    public String home(Model model) {
        //1. 查询所有分类
        List<Category> cs = categoryService.list();

        //2. 查询每个分类下的所有产品。
        categoryService.fillProducts(cs);

        //3. 查询每个分类下的热销商品，并按行分组，一行最多8个。
        categoryService.fillProductsByRow(cs);

        //4. 跳转到首页
        model.addAttribute("cs", cs);
        return "fore/home";
    }

    //登录页
    @RequestMapping("loginPage")
    public String loginPage(){
        return "fore/login";
    }

    //登录
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


        //查询购物车
        //查询订单
        return "redirect:forehome";
    }


    //注册页
    @RequestMapping("registerPage")
    public String register(){
        return "fore/register";
    }

    //注册
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

    //注册成功页
    @RequestMapping("registerSuccess")
    public String registerSuccess(){
        return "fore/registerSuccess";
    }

    //退出
    @RequestMapping("forelogout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:forehome";
    }

    //产品页
    @RequestMapping("foreproduct")
    public String product(@RequestParam("pid") int pid, Model model){
        //分类list
        List<Category> cs = categoryService.list();

        //产品
        Product product = productService.get(pid);
        //单个（类）图片
        List<ProductImage> pisSingle = productImageService.list(pid, "type_single");
        product.setProductSingleImages(pisSingle);
        //详情（类）图片
        List<ProductImage> pisDetail = productImageService.list(pid, "type_detail");
        product.setProductDetailImages(pisDetail);

        //产品评价


        model.addAttribute("cs",cs);
        model.addAttribute("p", product);
        return "fore/product";
    }


}
