package com.finstone.tmall.service.impl;

import com.finstone.tmall.entity.Category;
import com.finstone.tmall.entity.Page;
import com.finstone.tmall.mapper.CategoryMapper;
import com.finstone.tmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /*@Override
    public int total() {
        return categoryMapper.total();
    }*/

    /*@Override
    public List<Category> list(Page page) {
        return categoryMapper.list(page);
    }*/

    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }

    @Override
    public void add(Category category) {
        categoryMapper.add(category);
    }

    @Override
    public void delete(int id) {
        categoryMapper.delete(id);
    }

    @Override
    public void update(Category category) {
        categoryMapper.edit(category);
    }

    @Override
    public Category get(int id) {
        return categoryMapper.get(id);
    }

}
