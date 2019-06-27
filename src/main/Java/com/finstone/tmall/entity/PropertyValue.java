package com.finstone.tmall.entity;

public class PropertyValue {
    private Integer id;

    private Integer pid;  //产品id, Product.id,  一对多(产品)

    private Integer ptid; //属性id, Property.id, 一对多(属性)

    private String value; //属性值，与Property.name对应。

    //非数据库字段：手动添加, Property.name存放“属性名称”
    private Property property;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getPtid() {
        return ptid;
    }

    public void setPtid(Integer ptid) {
        this.ptid = ptid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}