package com.finstone.tmall.service;

import com.finstone.tmall.entity.Review;

import java.util.List;

public interface ReviewService {
    void add(Review review);

    void delete(int id);

    void update(Review review);

    Review get(int id);

    //查询指定产品的所有评论
    List<Review> list(int pid);

    //合计指定产品的评论数
    int getCount(int pid);
}
