package com.finstone.tmall.controller;

import com.finstone.tmall.entity.Category;
import com.finstone.tmall.entity.Page;
import com.finstone.tmall.entity.Property;
import com.finstone.tmall.service.CategoryService;
import com.finstone.tmall.service.PropertyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 注意：
 * 以下方法全部针对某个特定的产品 cid
 */
@Controller
@RequestMapping("")
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    @Autowired
    CategoryService categoryService;

    /**
     * 分页查询指定产品的所有属性
     * @param model
     * @param page
     * @param cid
     * @return
     */
    @RequestMapping("admin_property_list")
    public String list(Model model, Page page, int cid){
        Category c = categoryService.get(cid);

        //分页
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Property> ps = propertyService.list(cid);

        int total = (int) new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        page.setParam("&cid="+c.getId());

        model.addAttribute("ps",ps);
        model.addAttribute("c",c);
        model.addAttribute("page",page);
        return "admin/listProperty";
    }

    /**
     * 给指定产品添加一个属性
     * @param p
     * @return
     */
    @RequestMapping("admin_property_add")
    public String add(Model model, Property p){
        propertyService.add(p);
        return "redirect:/admin_property_list?cid="+p.getCid();
    }

    /**
     * 前往属性修改页
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("admin_property_edit")
    public String edit(Model model, int id){
        Property property = propertyService.get(id);
        Category category = categoryService.get(property.getCid());
        property.setCategory(category);
        model.addAttribute("p",property);
        //p.name提供初始值，用于修改
        //p.Category提供地址导航（面包屑）
        return "admin/editProperty";
    }

    /**
     * 修改指定产品的某个属性
     * @param p
     * @return
     */
    @RequestMapping("admin_property_update")
    public String update(Property p){
        propertyService.update(p);
        return "redirect:/admin_property_list?cid="+p.getCid();
    }

    /**
     * 删除指定产品的某个属性
     * @param id 属性编号
     * @return
     */
    @RequestMapping("admin_property_delete")
    public String delete(int id){
        Property property = propertyService.get(id);
        propertyService.delete(id);
        return "redirect:/admin_property_list?cid="+property.getCid();
    }

}