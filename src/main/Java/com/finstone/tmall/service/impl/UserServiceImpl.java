package com.finstone.tmall.service.impl;

import com.finstone.tmall.entity.User;
import com.finstone.tmall.entity.UserExample;
import com.finstone.tmall.mapper.UserMapper;
import com.finstone.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public void delete(int id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User get(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> list() {
        UserExample example = new UserExample();
        example.setOrderByClause("id desc");
        List<User> us = userMapper.selectByExample(example);
        return us;
    }

    @Override
    public boolean isExist(String name) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(name);
        List<User> us = userMapper.selectByExample(example);
        if(null != us && !us.isEmpty()){
            return true;
        }
        return false;
    }

    @Override
    public User get(String name, String password) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(name).andPasswordEqualTo(password);
        List<User> list = userMapper.selectByExample(example);
        if(list==null || list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
}
