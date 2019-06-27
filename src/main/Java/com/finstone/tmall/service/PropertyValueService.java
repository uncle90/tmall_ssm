package com.finstone.tmall.service;

import com.finstone.tmall.entity.Product;
import com.finstone.tmall.entity.PropertyValue;

import java.util.List;

/**
 * 属性值 PropertyValue
 */
public interface PropertyValueService {

    void init(Product product);

    PropertyValue get(int pid, int ptid);

    void delete(int id);

    void update(PropertyValue propertyValue);

    List<PropertyValue> list(int pid);
}
