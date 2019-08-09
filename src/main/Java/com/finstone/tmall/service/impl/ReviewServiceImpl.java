package com.finstone.tmall.service.impl;

import com.finstone.tmall.entity.Order;
import com.finstone.tmall.entity.Review;
import com.finstone.tmall.entity.ReviewExample;
import com.finstone.tmall.entity.User;
import com.finstone.tmall.mapper.ReviewMapper;
import com.finstone.tmall.service.OrderService;
import com.finstone.tmall.service.ReviewService;
import com.finstone.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewMapper reviewMapper;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Override
    @Transactional(propagation= Propagation.REQUIRED, rollbackForClassName = {"Exception"})
    public void add(Review review, int oid) {
        reviewMapper.insert(review);
        //更改订单状态
        Order order = orderService.get(oid);
        order.setStatus(OrderService.finish);
        orderService.update(order);
    }

    @Override
    public void delete(int id) {
        reviewMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Review review) {
        reviewMapper.updateByPrimaryKeySelective(review);
    }

    @Override
    public Review get(int id) {
        return reviewMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Review> list(int pid) {
        ReviewExample example = new ReviewExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id desc");
        List<Review> rws = reviewMapper.selectByExample(example);
        this.setUser(rws);
        return rws;
    }

    @Override
    public int getCount(int pid) {
        return list(pid).size();
    }

    @Override
    public void setUser(Review review) {
        User user = userService.get(review.getUid());
        review.setUser(user);
    }

    @Override
    public void setUser(List<Review> reviews) {
        for(Review review: reviews){
            this.setUser(review);
        }
    }
}
