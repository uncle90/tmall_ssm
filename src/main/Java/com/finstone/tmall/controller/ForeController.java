package com.finstone.tmall.controller;

import com.finstone.tmall.entity.Category;
import com.finstone.tmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class ForeController {

    @Autowired
    CategoryService categoryService;

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
//        return "admin/editCategory";
    }


}
