package com.finstone.tmall.controller;

import com.finstone.tmall.entity.Category;
import com.finstone.tmall.entity.Order;
import com.finstone.tmall.entity.OrderItem;
import com.finstone.tmall.entity.User;
import com.finstone.tmall.service.CategoryService;
import com.finstone.tmall.service.OrderItemService;
import com.finstone.tmall.service.OrderService;
import com.finstone.tmall.util.DateSyncUtil;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 购物相关：加入购物车、从购物车删除、立即购买、支付、确认收货等
 */
@Controller
@RequestMapping("")
public class ShoppingController {

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderService orderService;

    @Autowired
    CategoryService categoryService;

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

    /**
     * 从购物车删除
     * @return
     */
    @RequestMapping("foredeleteOrderItem")
    @ResponseBody
    public String foredeleteOrderItem(HttpSession session, int oiid){
        //检查会话，是否已登录
        User user = (User) session.getAttribute("user");
        if(user == null){
            return "fail";
        }
        orderItemService.delete(oiid);
        return "success";
    }

    /**
     * 修改购物车中的商品数量为 num
     * @return
     */
    @RequestMapping("forechangeOrderItem")
    @ResponseBody
    public String forechangeOrderItem(int pid, int number, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user==null){
            return "redirect:loginPage";
        }
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
            orderItem.setNumber(number);
            orderItemService.update(orderItem); //修改商品数量
        }
        return "success";
    }

    /**
     * 查看订单
     * @return
     */
    @RequestMapping("forebought")
    public String forebought(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        if(user==null){
            return "redirect:loginPage";
        }
        //历史订单
        List<Order> os = orderService.listByUser(user.getId());
        model.addAttribute("os",os);
        return "fore/bought";
    }

    /**
     * 结算页面（下单页面）
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
     * 提交订单。价格不需要额外保存，在订单项中。
     * @return
     */
    @RequestMapping("forecreateOrder")
    public String createOrder(Model model, HttpSession session, Order order){
        //用户&订单项
        User user = (User) session.getAttribute("user");
        List<OrderItem> ois = (List<OrderItem>) session.getAttribute("ois");
        //订单字段
        Date date = new Date();
        order.setOrderCode(DateSyncUtil.format(date)+ RandomUtils.nextInt(0,10000));//时间戳
        order.setCreateDate(date);
        order.setUid(user.getId());
        order.setStatus(OrderService.waitPay);
        //生成订单, 并返回订单总价.
        float total = orderService.add(order,ois);

        return "redirect:forealipay?total="+total+"&oid="+order.getId();
    }

    /**
     * 支付页
     * @return
     */
    @RequestMapping("forealipay")
    public String forealipay(Model model, String oid, String total){
        Order order = orderService.get(Integer.parseInt(oid));
        //已付款，前往订单页查看
        if(order.getPayDate() != null){
            return "fore/payed";
        }
        model.addAttribute("oid",oid);
        model.addAttribute("total",total);
        return "fore/alipay";
    }

    /**
     * 完成支付
     * @param oid 订单编号
     * @param total 金额
     * @return
     */
    @RequestMapping("forepayed")
    public String forepayed(String oid, String total){
        Order order = orderService.get(Integer.parseInt(oid));
        //更改订单状态
        order.setStatus(OrderService.waitDelivery);
        order.setPayDate(new Date());
        orderService.update(order);
        return "fore/payed";
    }

    /**
     * 删除订单。假删除，修改状态，不删除数据。
     * @return
     */
    @RequestMapping("foredeleteOrder")
    @ResponseBody
    public String foredeleteOrder(HttpSession session, int oid){
        User user = (User) session.getAttribute("user");
        if(user == null){
            return "fail";
        }
        Order order = orderService.get(oid);
        order.setStatus(OrderService.delete);
        orderService.update(order);
        return "success";
    }

    /**
     * 去确认收货页
     * @return
     */
    @RequestMapping("foreconfirmPay")
    public String foreconfirmPay(HttpSession session, Model model, int oid){
        //1. 检查用户
        User user = (User) session.getAttribute("user");
        if(user==null){
            return "redirect:loginPage";
        }
        //2. 查询所有分类
        List<Category> cs = categoryService.list();
        //3. 查询订单 & 订单项
        Order order = orderService.get(oid);
        orderItemService.fill(order);

        model.addAttribute("cs",cs);
        model.addAttribute("o",order);
        return "fore/confirmPay";
    }

    /**
     * 确认收货
     * @return
     */
    @RequestMapping("foreorderConfirmed")
    public String foreorderConfirmed(HttpSession session, int oid){
        //1. 检查用户
        User user = (User) session.getAttribute("user");
        if(user==null){
            return "redirect:loginPage";
        }
        //2.确认收货
        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitReview);
        order.setConfirmDate(new Date());
        orderService.update(order);
        return "fore/orderConfirmed";
    }

}
