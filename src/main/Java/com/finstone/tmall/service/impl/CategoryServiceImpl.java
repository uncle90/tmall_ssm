package com.finstone.tmall.service.impl;

import com.finstone.tmall.entity.*;
import com.finstone.tmall.mapper.CategoryMapper;
import com.finstone.tmall.mapper.ProductMapper;
import com.finstone.tmall.service.CategoryService;
import com.finstone.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductService productService;

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

    @Override
    public void fill(Category c) {
        List<Product> ps = productService.list(c.getId());
        c.setProducts(ps);
    }

    @Override
    public void fillProducts(List<Category> cs) {
        for(Category c: cs){
            fill(c);
        }
    }

    @Override
    public void fillProductsByRow(List<Category> cs) {
        int productNumberEachRow = 8;
        for(Category c: cs){
            List<Product> ps = productService.list(c.getId());
            List<List<Product>> productsByRow = new ArrayList<>();
            for (int i = 0; i < ps.size(); i+=productNumberEachRow){
                int end = i+productNumberEachRow>ps.size()?ps.size():i+productNumberEachRow; //检查长度是否越界
                List<Product> productsOfRow = ps.subList(i,end);//按行截取产品列表，作为热销产品（行）[i,end)
                productsByRow.add(productsOfRow);
            }
            c.setProductsByRow(productsByRow);
        }
    }

}
