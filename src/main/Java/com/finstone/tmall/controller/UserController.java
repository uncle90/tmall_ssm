package com.finstone.tmall.controller;

import com.finstone.tmall.entity.Page;
import com.finstone.tmall.entity.User;
import com.finstone.tmall.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("admin_user_list")
    public String list(Model model, Page page){
        //分页
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<User> us = userService.list();

        //通过PageInfo获取总数
        int total = (int) new PageInfo<>(us).getTotal();
        page.setTotal(total);

        model.addAttribute("page", page);
        model.addAttribute("us", us);
        return "admin/listUser";
    }



}
