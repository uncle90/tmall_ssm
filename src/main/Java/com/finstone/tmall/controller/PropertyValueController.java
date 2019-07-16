package com.finstone.tmall.controller;

import com.finstone.tmall.entity.Product;
import com.finstone.tmall.entity.PropertyValue;
import com.finstone.tmall.service.CategoryService;
import com.finstone.tmall.service.ProductService;
import com.finstone.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PropertyValueController {

    @Autowired
    PropertyValueService propertyValueService;

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    /**
     * 产品属性list查看
     * @param pid = PropertyValue.pid = Product.id
     * @param model
     * @return
     */
    @RequestMapping("admin_propertyValue_edit")
    public String list(int pid, Model model){
        Product product = productService.get(pid);
        //属性值初始化
        propertyValueService.init(product);
        //属性值列表
        List<PropertyValue> pvs = propertyValueService.list(pid);
        model.addAttribute("pvs", pvs);   //属性list
        model.addAttribute("p", product); //面包屑
        return "admin/editPropertyValue";
    }

    /**
     * 修改产品属性值
     * @param propertyValue
     * @param model
     * @return
     */
    @RequestMapping("admin_propertyValue_update")
    @ResponseBody
    public String update(PropertyValue propertyValue, Model model){
        propertyValueService.update(propertyValue);
        return "success";
    }
}
