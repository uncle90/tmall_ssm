package com.finstone.tmall.controller;

import com.finstone.tmall.entity.Order;
import com.finstone.tmall.entity.Page;
import com.finstone.tmall.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping("admin_order_list")
    public String list(Model model, Page page){
        //指定分页参数
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Order> orders = orderService.list();
        //通过PageInfo获取总数
        int total = (int) new PageInfo<>(orders).getTotal();
        page.setTotal(total);

        model.addAttribute("page",page);
        model.addAttribute("os",orders);
        return "admin/listOrder";
    }

    /**
     * 订单后台发货
     * @param id
     * @return
     */
    @RequestMapping("admin_order_delivery")
    public String delivery(int id){
        Order order = orderService.get(id);
        order.setStatus(OrderService.waitConfirm);
        order.setDeliveryDate(new Date());
        orderService.update(order);
        return "redirect:admin_order_list";
    }

}