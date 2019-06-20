package com.finstone.tmall.service.impl;

import com.finstone.tmall.entity.Property;
import com.finstone.tmall.entity.PropertyExample;
import com.finstone.tmall.mapper.PropertyMapper;
import com.finstone.tmall.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    PropertyMapper propertyMapper;

    @Override
    public void add(Property property) {
        propertyMapper.insert(property);
    }

    @Override
    public void delete(int id) {
        propertyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Property property) {
        propertyMapper.updateByPrimaryKeySelective(property);
    }

    @Override
    public Property get(int id) {
        return propertyMapper.selectByPrimaryKey(id);
    }

    /**
     * 分页查询指定产品的所有属性
     * @param cid 产品id
     * @return
     */
    @Override
    public List<Property> list(int cid) {
        PropertyExample example = new PropertyExample();
        //条件
        example.createCriteria().andCidEqualTo(cid);
        //排序
        example.setOrderByClause("id asc");
        return propertyMapper.selectByExample(example);
    }
}
