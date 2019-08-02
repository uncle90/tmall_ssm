package com.finstone.tmall.service.impl;

import com.finstone.tmall.entity.Review;
import com.finstone.tmall.entity.ReviewExample;
import com.finstone.tmall.mapper.ReviewMapper;
import com.finstone.tmall.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewMapper reviewMapper;

    @Override
    public void add(Review review) {
        reviewMapper.insert(review);
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
        return rws;
    }

    @Override
    public int getCount(int pid) {
        return list(pid).size();
    }
}
