package com.finstone.tmall.service.impl;

import com.finstone.tmall.entity.Order;
import com.finstone.tmall.entity.OrderExample;
import com.finstone.tmall.mapper.OrderMapper;
import com.finstone.tmall.service.OrderItemService;
import com.finstone.tmall.service.OrderService;
import com.finstone.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ProductService productService;

    @Override
    public void add(Order order) {
    }

    @Override
    public void delete(int id) {
    }

    @Override
    public void update(Order order) {
    }

    @Override
    public List<Order> list() {
        OrderExample example1 = new OrderExample();
        example1.setOrderByClause("id desc");
        List<Order> orders = orderMapper.selectByExample(example1);
        orderItemService.fill(orders);
        return orders;
    }

}
