package com.finstone.tmall.controller;

import com.finstone.tmall.entity.Category;
import com.finstone.tmall.entity.Page;
import com.finstone.tmall.entity.Property;
import com.finstone.tmall.entity.PropertyValue;
import com.finstone.tmall.service.CategoryService;
import com.finstone.tmall.service.PropertyService;
import com.finstone.tmall.service.PropertyValueService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 注意：
 * 以下方法全部针对某个特定的分类 cid
 */
@Controller
@RequestMapping("")
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    PropertyValueService propertyValueService;

    /**
     * 分页查询指定分类的所有属性
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
     * 给指定分类添加一个属性
     * @param p
     * @return
     */
    @RequestMapping("admin_property_add")
    public String add(Property p){
        propertyService.add(p);
        return "redirect:admin_property_list?cid="+p.getCid();
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
     * 修改指定分类的某个属性
     * @param p
     * @return
     */
    @RequestMapping("admin_property_update")
    public String update(Property p){
        propertyService.update(p);
        return "redirect:admin_property_list?cid="+p.getCid();
    }

    /**
     * 删除指定分类的某个属性，同步删除产品属性（值，外键）
     * @param id 属性编号 = property.id = PropertyValue.ptid
     * @return
     */
    @RequestMapping("admin_property_delete")
    public String delete(int id){
        //逐个删除产品属性（值）
        List<PropertyValue> pvs = propertyValueService.listByPropertyId(id);
        for(PropertyValue pv: pvs){
            propertyValueService.delete(pv.getId());
        }
        //删除分类属性
        Property property = propertyService.get(id);
        propertyService.delete(id);
        return "redirect:admin_property_list?cid="+property.getCid();
    }

}