package com.finstone.tmall.service.impl;

import com.finstone.tmall.entity.Order;
import com.finstone.tmall.entity.OrderItem;
import com.finstone.tmall.entity.OrderItemExample;
import com.finstone.tmall.entity.Product;
import com.finstone.tmall.mapper.OrderItemMapper;
import com.finstone.tmall.service.OrderItemService;
import com.finstone.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    ProductService productService;

    @Override
    public void add(OrderItem order) {
    }

    @Override
    public void delete(int id) {
    }

    @Override
    public void update(OrderItem orderItem) {
    }

    @Override
    public List<OrderItem> list() {
        return null;
    }

    @Override
    public void fill(List<Order> orders) {
        for (Order order: orders){
            fill(order);
        }
    }

    @Override
    public void fill(Order order) {
        //订单项
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andOidEqualTo(order.getId());//订单编号
        example.setOrderByClause("id desc");
        List<OrderItem> ois = orderItemMapper.selectByExample(example);
        order.setOrderItems(ois);

        //订单中的商品数量
        Integer count = 0;

        //订单总金额
        float total = 0;
        if(ois != null && !ois.isEmpty()){
            for(OrderItem orderItem: ois){
                //订单项对应的商品
                this.setProduct(orderItem);

                count += orderItem.getNumber();
                total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
            }
        }
        order.setTotalNumber(count);
        order.setTotal(total);
    }

    @Override
    public void setProduct(OrderItem orderItem) {
        Product product = productService.get(orderItem.getPid());
        orderItem.setProduct(product);
    }

}
