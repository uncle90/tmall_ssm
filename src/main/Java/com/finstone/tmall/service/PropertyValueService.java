package com.finstone.tmall.service;

import com.finstone.tmall.entity.Product;
import com.finstone.tmall.entity.PropertyValue;

import java.util.List;

/**
 * 属性值 PropertyValue
 */
public interface PropertyValueService {

    /**
     * 根据产品所属分类，初始化指定产品的属性（名称）
     * 分类：Property.cid
     * 属性：Property.name
     * 初始化：insert into PropertyValue(ptid, pid, value) value(?, ?, null)
     * @param product
     */
    void init(Product product);

    PropertyValue get(int pid, int ptid);

    void delete(int id);

    void update(PropertyValue propertyValue);

    /**
     * 查询指定产品(pid=product.id)的所有属性
     * 属性名称: PropertyValue.Property.name
     * 属性值  : PropertyValue.value
     * @param pid
     * @return
     */
    List<PropertyValue> list(int pid);

    /**
     * 查询指定属性名称下的所有属性值（产品不同） for 删除分类下的属性
     * @param ptid = Property.id 属性编号
     * @return
     */
    List<PropertyValue> listByPropertyId(int ptid);
}
