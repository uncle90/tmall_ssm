package com.finstone.tmall.service.impl;

import com.finstone.tmall.entity.Order;
import com.finstone.tmall.entity.OrderExample;
import com.finstone.tmall.entity.OrderItem;
import com.finstone.tmall.mapper.OrderMapper;
import com.finstone.tmall.service.OrderItemService;
import com.finstone.tmall.service.OrderService;
import com.finstone.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ProductService productService;

    /**
     * 创建订单,更新订单项. 遇到任何异常Exception都回滚.
     * @param order
     * @param ois
     */
    @Override
    @Transactional(propagation= Propagation.REQUIRED, rollbackForClassName = {"Exception"})
    public float add(Order order, List<OrderItem> ois) {
        float total = 0;
        //创建订单
        orderMapper.insert(order);
        int oid = order.getId();
        //更新订单项 uid
        for(OrderItem orderItem: ois){
            orderItem.setOid(oid);
            orderItemService.update(orderItem);
            //订单总价
            total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
        }
        return total;
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

    @Override
    public List<Order> listByUser(int uid) {
        OrderExample example = new OrderExample();
        example.createCriteria().andUidEqualTo(uid);
        example.setOrderByClause("id desc");
        List<Order> orders = orderMapper.selectByExample(example);
        orderItemService.fill(orders);
        return orders;
    }
}
