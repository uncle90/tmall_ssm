package com.finstone.tmall.mapper;

import com.finstone.tmall.entity.Category;
import com.finstone.tmall.entity.Page;

import java.util.List;

public interface CategoryMapper {
    //int total(); //手工分页

    //List<Category> list(Page page); //手工分页

    List<Category> list(); //Mybatis插件分页

    void add(Category category);

    void delete(int id);

    void edit(Category category);

    Category get(int id);
}
