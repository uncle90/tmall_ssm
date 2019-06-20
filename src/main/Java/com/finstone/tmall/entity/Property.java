package com.finstone.tmall.entity;

public class Property {
    private Integer id;

    private Integer cid;

    private String name;

    /*非数据库字段：手动添加. 在属性查询页、添加页、编辑页提供关联信息，包括category.id、category.name等。*/
    private Category category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}