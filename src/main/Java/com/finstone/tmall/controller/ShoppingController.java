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

        return "redirect:forebuy?oiid="+orderItem.getOid();
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


}
