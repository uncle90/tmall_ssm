package com.finstone.tmall.mapper;

import com.finstone.tmall.entity.Category;
import com.finstone.tmall.entity.Page;

import java.util.List;

public interface CategoryMapper {
    int total();

    List<Category> list(Page page);

    void add(Category category);

    void delete(int id);

    void edit(Category category);

    Category get(int id);
}
