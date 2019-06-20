package com.finstone.tmall.mapper;

import com.finstone.tmall.entity.Category;
import com.finstone.tmall.entity.CategoryExample;
import java.util.List;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExample(CategoryExample example);

    Category selectByPrimaryKey(Integer id);

    /**
     * 与updateByPrimaryKey不同，只改非null属性对应的字段。
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}