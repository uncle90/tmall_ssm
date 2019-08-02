package com.finstone.tmall.service.impl;

import com.finstone.tmall.entity.Category;
import com.finstone.tmall.entity.Product;
import com.finstone.tmall.entity.ProductExample;
import com.finstone.tmall.entity.ProductImage;
import com.finstone.tmall.mapper.ProductMapper;
import com.finstone.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    OrderItemService orderItemService;

    @Override
    public void add(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Product product) {
        productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public Product get(int id) {
        Product product = productMapper.selectByPrimaryKey(id);
        //设置 Category 属性
        setCategory(product);
        //设置封面图片 firstProductImage 属性
        setFirstProductImage(product);
        return product;
    }

    /**
     * 分页查询指定分类(category)下的所有产品(product)
     * @param cid
     * @return
     */
    @Override
    public List<Product> list(int cid) {
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id asc");
        List<Product> ps = productMapper.selectByExample(example);
        //遍历list，设置封面图片 firstProductImage 属性
        setFirstProductImage(ps);
        //遍历list，设置 category 属性
        setCategory(ps);
        return ps;
    }


    /**
     * 给单个Product设置Category属性
     * @param product
     */
    public void setCategory(Product product){
        int id = product.getCid();
        Category category = categoryService.get(id);
        product.setCategory(category);
    }

    /**
     * 给集合中的所有Product设置Category属性
     * @param ps
     */
    public void setCategory(List<Product> ps){
        for(Product p : ps)
            setCategory(p);
    }

    @Override
    public void setFirstProductImage(Product product) {
        List<ProductImage> pis = productImageService.list(product.getId(), ProductImageService.type_single);
        if(null != pis && !pis.isEmpty()){
            ProductImage pi = pis.get(0);
            product.setFirstProductImage(pi);
        }
    }

    @Override
    public void setFirstProductImage(List<Product> ps) {
        for(Product p: ps){
            this.setFirstProductImage(p);
        }
    }

    @Override
    public void setSaleCountAndReviewCount(Product product) {
        product.setReviewCount(reviewService.getCount(product.getId()));
        product.setSaleCount(orderItemService.getSaleCount(product.getId()));
    }

    @Override
    public void setSaleCountAndReviewCount(List<Product> ps) {
        for(Product product: ps){
            this.setFirstProductImage(product);
        }
    }
}
