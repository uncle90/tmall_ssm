package com.finstone.tmall.service.impl;

import com.finstone.tmall.entity.Category;
import com.finstone.tmall.entity.CategoryExample;
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
        //return categoryMapper.list();
        CategoryExample example = new CategoryExample();
        example.setOrderByClause("id desc");
        return categoryMapper.selectByExample(example);
    }

    @Override
    public void add(Category category) {
        //categoryMapper.add(category);
        categoryMapper.insert(category);
    }

    @Override
    public void delete(int id) {
        //categoryMapper.delete(id);
        categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Category category) {
        //categoryMapper.edit(category);
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public Category get(int id) {
        //return categoryMapper.get(id);
        return categoryMapper.selectByPrimaryKey(id);
    }

}
