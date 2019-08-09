package com.finstone.tmall.service;

import com.finstone.tmall.entity.Order;
import com.finstone.tmall.entity.Review;

import java.util.List;

public interface ReviewService {
    //添加商品评价，更改订单状态
    void add(Review review, int oid);

    void delete(int id);

    void update(Review review);

    Review get(int id);

    //查询指定产品的所有评论
    List<Review> list(int pid);

    //合计指定产品的评论数
    int getCount(int pid);

    //设置评论用户
    void setUser(Review review);

    //设置评论用户
    void setUser(List<Review> reviews);
}
