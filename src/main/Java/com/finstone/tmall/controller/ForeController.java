package com.finstone.tmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class ForeController {

    /**
     * 首页
     * 一、功能
     * 1. 在横向导航栏上提供4个分类连接
     * 2. 在纵向导航栏上提供全部17个分类连接
     * 3. 当鼠标移动到某一个纵向分类连接的时候，显示这个分类下的推荐产品列表
     * 4. 按照每种分类显示5个商品的方式，显示所有17种分类
     *
     * 二、页面结构
     * <%@include file="../include/fore/header.jsp"%>
     * <%@include file="../include/fore/top.jsp"%>                      导航栏
     * <%@include file="../include/fore/search.jsp"%>                   搜索框
     * <%@include file="../include/fore/home/homePage.jsp"%>            页面主体
     *     <%@include file="categoryAndcarousel.jsp"%>                  导航和轮播、分类和产品推荐
     *              rightMenu：<a href="forecategory?cid=${c.id}">      导航
     *          	<%@include file="categoryMenu.jsp" %>               商品分类
     *          	<%@include file="productsAsideCategorys.jsp" %>     推荐商品（按类别分组）
     *          	<%@include file="carousel.jsp" %>                   轮播
     *     <%@include file="homepageCategoryProducts.jsp"%>             产品列表
     * <%@include file="../include/fore/footer.jsp"%>
     */
    @RequestMapping("")
    public String home(){
        return "fore/home";
    }




}
