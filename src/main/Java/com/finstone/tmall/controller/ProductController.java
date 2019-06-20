package com.finstone.tmall.controller;

import com.finstone.tmall.entity.Category;
import com.finstone.tmall.entity.Page;
import com.finstone.tmall.entity.Product;
import com.finstone.tmall.service.CategoryService;
import com.finstone.tmall.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * 注意：
 * 以下方法全部针对某个特定的分类 cid
 */
@Controller
@RequestMapping("")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    /**
     * 分页查询指定分类下的所有产品
     * @param model
     * @param page 分页bean
     * @param cid 分类id
     * @return
     */
    @RequestMapping("admin_product_list")
    public String list(Model model, Page page, int cid){
        Category c = categoryService.get(cid);

        //分页
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Product> ps = productService.list(cid);

        int total = (int) new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        //产品分页都是基于当前分类下的分页，所以分页的时候需要传递这个cid
        page.setParam("&cid="+c.getId());

        model.addAttribute("ps",ps);
        model.addAttribute("c",c);
        model.addAttribute("page", page);
        return "admin/listProduct";
    }

    /**
     * 给指定分类添加一种产品
     * @param p 产品
     * @return
     */
    @RequestMapping("admin_product_add")
    public String add(Model model, Product p){
        p.setCreateDate(new Date());
        productService.add(p);
        return "redirect:/admin_product_list?cid="+p.getCid();
    }

    /**
     * 前往产品修改页
     * @param model
     * @param id 产品编号
     * @return
     */
    @RequestMapping("admin_product_edit")
    public String edit(Model model, int id){
        Product product = productService.get(id);
        /*//放在了 Service 层
        Category category = categoryService.get(product.getCid());
        product.setCategory(category);*/
        model.addAttribute("p",product);
        //p.name提供初始值，用于修改
        //p.Category提供地址导航（面包屑）
        return "admin/editProduct";
    }

    /**
     * 修改指定分类下的某种产品
     * @param p 产品
     * @return
     */
    @RequestMapping("admin_product_update")
    public String update(Product p){
        productService.update(p);
        return "redirect:/admin_product_list?cid="+p.getCid();
    }

    /**
     * 删除指定分类下的某种产品
     * @param id 产品编号
     * @return
     */
    @RequestMapping("admin_product_delete")
    public String delete(int id){
        Product product = productService.get(id);
        productService.delete(id);
        return "redirect:/admin_product_list?cid="+product.getCid();
    }

}