package com.finstone.tmall.mapper;

import com.finstone.tmall.entity.ProductImage;
import com.finstone.tmall.entity.ProductImageExample;
import java.util.List;

public interface ProductImageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductImage record);

    int insertSelective(ProductImage record);

    List<ProductImage> selectByExample(ProductImageExample example);

    ProductImage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductImage record);

    int updateByPrimaryKey(ProductImage record);
}