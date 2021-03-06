package com.finstone.tmall.service;

import com.finstone.tmall.entity.Property;

import java.util.List;

public interface PropertyService {
    void add(Property property);

    void delete(int id);

    void update(Property property);

    Property get(int id);

    //按产品类别(Category) cid 查询
    List<Property> list(int cid);

}
