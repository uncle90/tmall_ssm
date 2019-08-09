package com.finstone.tmall.service;

import com.finstone.tmall.entity.Order;
import com.finstone.tmall.entity.OrderItem;

import java.util.List;

public interface OrderService {

    //订单状态常量
    String waitPay = "waitPay";            //待支付
    String waitDelivery = "waitDelivery"; //待发货
    String waitConfirm = "waitConfirm";   //待确认收货
    String waitReview = "waitReview";     //待评价
    String finish = "finish";              //订单结束
    String delete = "delete";              //订单删除

    //创建订单,更新订单项.
    float add(Order order, List<OrderItem> ois);

    void delete(int id);

    void update(Order order);

    //根据编号查询订单
    Order get(int id);

    //列出所有订单，以及订单项
    List<Order> list();

    //列出用户的所有订单，以及订单项，不含已删除的。
    List<Order> listByUser(int uid);

}