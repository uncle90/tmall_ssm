package com.finstone.tmall.service;

import com.finstone.tmall.entity.Order;
import com.finstone.tmall.entity.OrderItem;

import java.util.List;

public interface OrderItemService {

    void add(OrderItem order);

    void delete(int id);

    void update(OrderItem orderItem);

    OrderItem get(int id);

    List<OrderItem> list();

    //查询用户购物车中的商品（未提交到订单）
    List<OrderItem> listByUser(int uid);

    //设置Order.OrderItem属性
    void fill(List<Order> orders);

    //设置Order.OrderItem属性
    void fill(Order order);

    //设置订单项对应的商品
    void setProduct(OrderItem orderItem);

    //设置订单项对应的商品
    void setProduct(List<OrderItem> ois);

    //合计指定产品的销量
    int getSaleCount(int pid);

}
