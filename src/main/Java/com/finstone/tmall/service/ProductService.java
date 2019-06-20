package com.finstone.tmall.service;

import com.finstone.tmall.entity.Product;

import java.util.List;

public interface ProductService {

    void add(Product product);

    void delete(int id);

    void update(Product product);

    Product get(int id);

    //按产品类别(Category) cid 查询
    List<Product> list(int cid);
}
