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

    //设置产品的封面图片，取id最大的type_single类图片
    void setFirstProductImage(Product product);

    void setFirstProductImage(List<Product> ps);

    //产品销量和累计评价数
    void setSaleCountAndReviewCount(Product product);

    //产品销量和累计评价数
    void setSaleCountAndReviewCount(List<Product> ps);

    //产品搜索，根据名称模糊查询
    List<Product> search(String keyword);
}
