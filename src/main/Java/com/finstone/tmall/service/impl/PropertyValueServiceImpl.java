package com.finstone.tmall.service.impl;

import com.finstone.tmall.entity.*;
import com.finstone.tmall.mapper.PropertyMapper;
import com.finstone.tmall.mapper.PropertyValueMapper;
import com.finstone.tmall.service.PropertyService;
import com.finstone.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 注意：PropertyValue的管理，没有插入只有修改，但每种类别的产品都有【属性名称】管理。
 * 因此，PropertyValue查询前，需要刷新——多退少补！！！
 *
 * Property.id = PropertyValue.ptid
 * PropertyValue.pid = Product.id,
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService {

    @Autowired
    PropertyValueMapper propertyValueMapper; //属性值

    @Autowired
    PropertyService propertyService; //属性名称

    /**
     * 按产品所属类别，初始化/刷新每种产品的属性名称。
     * @param product
     */
    @Override
    public void init(Product product) {
        List<Property> pts = propertyService.list(product.getCid());

        for(Property pt: pts){
            int pid = product.getId();
            int ptid = pt.getId();
            PropertyValue propertyValue = get(pid, ptid);
            if(propertyValue == null){
                propertyValue = new PropertyValue();
                propertyValue.setPid (pid);
                propertyValue.setPtid(ptid);
                propertyValueMapper.insert(propertyValue);
            }
        }
    }

    /**
     * 根据产品编号 pid 和属性编号 ptid，获取属性值。
     * @param pid
     * @param ptid
     * @return
     */
    @Override
    public PropertyValue get(int pid, int ptid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria()
                .andPidEqualTo(pid)
                .andPtidEqualTo(ptid);
        List<PropertyValue> pvs = propertyValueMapper.selectByExample(example);
        if(pvs.isEmpty()){
            return null;
        }else{
            return pvs.get(0);
        }
    }

    @Override
    public void delete(int id) {
        propertyValueMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(PropertyValue pv) {
        propertyValueMapper.updateByPrimaryKeySelective(pv);
    }

    /**
     * 查询指定产品的所有属性键值对（属性名，属性值）.
     * @param pid = PropertyValue.pid = Product.id,
     * @return
     */
    @Override
    public List<PropertyValue> list(int pid) {
        //属性值list
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id asc");
        List<PropertyValue> pvs = propertyValueMapper.selectByExample(example);

        //属性名
        for(PropertyValue pv: pvs){
            Property property = propertyService.get(pv.getPtid());
            pv.setProperty(property);
        }
        return pvs;
    }
}
