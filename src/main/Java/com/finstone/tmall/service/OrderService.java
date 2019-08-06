package com.finstone.tmall.service;

import com.finstone.tmall.entity.Order;

import java.util.List;

public interface OrderService {

    //订单状态常量
    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

    void add(Order order);

    void delete(int id);

    void update(Order order);

    //列出所有订单，以及订单项
    List<Order> list();

    //列出用户的所有订单，以及订单项
    List<Order> listByUser(int uid);

}