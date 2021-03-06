package com.finstone.tmall.service;

import com.finstone.tmall.entity.User;

import java.util.List;

public interface UserService {

    void add(User user);

    void delete(int id);

    void update(User user);

    User get(int id);

    List<User> list();

    //判断用户名是否被占用
    boolean isExist(String name);

    //检验账号密码
    User get(String name, String password);
}
