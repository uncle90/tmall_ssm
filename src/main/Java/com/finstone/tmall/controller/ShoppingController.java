package com.finstone.tmall.controller;

import com.finstone.tmall.entity.OrderItem;
import com.finstone.tmall.entity.User;
import com.finstone.tmall.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物相关：加入购物车、从购物车删除、立即购买、支付、确认收货等
 */
@Controller
@RequestMapping("")
public class ShoppingController {

    @Autowired
    OrderItemService orderItemService;

    /**
     * 立即购买某种商品。如果购物车有同类商品，则追加数量，合并购买。
     * @param model
     * @param pid
     * @param num
     * @return
     */
    @RequestMapping("forebuyone")
    public String forebuyone(Model model,
                             HttpSession session,
                             @RequestParam("pid") int pid,
                             @RequestParam("num") int num){
        User user = (User) session.getAttribute("user");
        //检查购物车是否有同类商品
        boolean exist = false;
        OrderItem orderItem = null;
        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        if(ois!=null && !ois.isEmpty()){
            for(OrderItem oi: ois){
                if(oi.getPid()==pid){//自动拆箱
                    orderItem = oi;
                    exist = true;
                    break;
                }
            }
        }
        //修改订单项
        if(exist){
            orderItem.setNumber(orderItem.getNumber()+num);
            orderItemService.update(orderItem); //追加商品数量
        }else{
            orderItem = new OrderItem(pid, user.getId(), num);
            orderItemService.add(orderItem);    //新建订单项
        }
        //设置订单项对应的商品
        orderItemService.setProduct(orderItem);

        return "redirect:forebuy?oiid="+orderItem.getId();
    }

    /**
     * 把某种商品加入购物车。如果购物车有同类商品，则追加数量。与 forebuyone 类似。
     * @return
     */
    @RequestMapping("foreaddCart")
    @ResponseBody
    public String foreaddCart(HttpSession session,
                              @RequestParam("pid") int pid,
                              @RequestParam("num") int num){
        User user = (User) session.getAttribute("user");
        //检查购物车是否有同类商品
        boolean exist = false;
        OrderItem orderItem = null;
        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        if(ois!=null && !ois.isEmpty()){
            for(OrderItem oi: ois){
                if(oi.getPid()==pid){//自动拆箱
                    orderItem = oi;
                    exist = true;
                    break;
                }
            }
        }
        //修改订单项
        if(exist){
            orderItem.setNumber(orderItem.getNumber()+num);
            orderItemService.update(orderItem); //追加商品数量
        }else{
            orderItem = new OrderItem(pid, user.getId(), num);
            orderItemService.add(orderItem);    //新建订单项
        }
        return "success";
    }

    /**
     * 结算（订单提交）页面
     * @param model
     * @param session
     * @param oiid 多个订单项
     * @return
     */
    @RequestMapping("forebuy")
    public String forebuy(Model model, HttpSession session, String[] oiid){
        List<OrderItem> ois = new ArrayList<>();
        float total = 0;
        for(String idstr: oiid){
            int id = Integer.parseInt(idstr);
            OrderItem orderItem = orderItemService.get(id);//获取订单项
            orderItemService.setProduct(orderItem);//设置产品属性
            total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();//总价
            ois.add(orderItem);
        }
        model.addAttribute("total",total);
        session.setAttribute("ois",ois);//把订单项放入回话，给其他页面使用
        return "fore/buy";
    }

    /**
     * 查看购物车。已加入购物车的商品从session中获取。
     * @return
     */
    @RequestMapping("forecart")
    public String forecart(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        if(user==null){
            return "redirect:loginPage";
        }
        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        orderItemService.setProduct(ois);
        model.addAttribute("ois", ois);
        return "fore/cart";
    }

}
