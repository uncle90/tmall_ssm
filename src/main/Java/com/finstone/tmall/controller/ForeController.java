package com.finstone.tmall.controller;

import com.finstone.tmall.entity.*;
import com.finstone.tmall.service.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("")
public class ForeController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    PropertyValueService propertyValueService;

    @Autowired
    ReviewService reviewService;

    /**
     * 首页
     * 1. 在横向导航栏上提供4个分类连接
     * 2. 在纵向导航栏上提供全部17个分类连接
     * 3. 当鼠标移动到某一个纵向分类连接的时候，显示这个分类下的推荐产品列表
     * 4. 按照每种分类显示5个商品的方式，显示所有17种分类
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

    /**
`     * 前台产品页
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping("foreproduct")
    public String product(@RequestParam("pid") int pid, Model model){
        /*//分类，放在拦截器中
        List<Category> cs = categoryService.list();*/

        //产品&图片信息
        Product product = productService.get(pid);
        List<ProductImage> pisSingle = productImageService.list(pid, ProductImageService.type_single); //单个（类）图片
        product.setProductSingleImages(pisSingle);
        List<ProductImage> pisDetail = productImageService.list(pid, ProductImageService.type_detail); //详情（类）图片
        product.setProductDetailImages(pisDetail);

        //商品详情（产品属性）
        List<PropertyValue> pvs = propertyValueService.list(pid);

        //产品评价
        List<Review> reviews = reviewService.list(pid);

        //销量和累计评价
        productService.setSaleCountAndReviewCount(product);

        /*model.addAttribute("cs",cs);*/
        model.addAttribute("p", product);
        model.addAttribute("pvs",pvs);
        model.addAttribute("reviews",reviews);
        return "fore/product";
    }

    /**
     * 前台分类页
     * @param cid
     * @param sort 排序方式(可选)
     * @param model
     * @return
     */
    @RequestMapping("forecategory")
    public String forecategory(int cid, String sort, Model model){
        /*List<Category> cs = categoryService.list();   //搜索框下的分类， 放在拦截器中*/
        Category category = categoryService.get(cid); //当前分类
        categoryService.fill(category);               //当前分类下的产品
        productService.setSaleCountAndReviewCount(category.getProducts()); //产品销量&评价数量 for 排序
        //产品排序
        if(sort != null){
            switch (sort){
                case "all": //热门（销量*评论数）
                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return o2.getSaleCount()*o2.getReviewCount() - o1.getSaleCount()*o1.getReviewCount();
                        }
                    });
                    break;
                case "review": //评价数多
                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return o2.getReviewCount() - o1.getReviewCount();
                        }
                    });
                    break;
                case "date": //创建日期近
                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return o2.getCreateDate().compareTo(o1.getCreateDate());
                        }
                    });
                    break;
                case "saleCount"://销量多
                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return o2.getSaleCount() - o1.getSaleCount();
                        }
                    });
                    break;
                case "price"://价格低
                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return (int) (o1.getPromotePrice() - o2.getPromotePrice());
                        }
                    });
                    break;
            }
        }

        /*model.addAttribute("cs",cs);*/
        model.addAttribute("c",category);
        return "fore/category";
    }

    /**
     * 商品搜索
     * @return
     */
    @RequestMapping("foresearch")
    public String search(String keyword, Model model){
        PageHelper.offsetPage(0,20); //取前20条记录
        List<Product> ps = productService.search(keyword);
        model.addAttribute("ps",ps);
        return "fore/searchResult";
    }

}
