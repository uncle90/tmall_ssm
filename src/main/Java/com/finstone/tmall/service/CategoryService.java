package com.finstone.tmall.service;

import com.finstone.tmall.entity.Category;
import com.finstone.tmall.entity.Page;

import java.util.List;

public interface CategoryService {
    /**
     * 获取记录总数
     * @return
     */
    //int total();

    /**
     * （分页）获取所有记录
     * @return
     */
    //List<Category> list(Page page);
    List<Category> list();

    /**
     * 添加一条记录
     * @param category
     */
    void add(Category category);

    /**
     * 删除一条记录
     * @param id
     */
    void delete(int id);

    /**
     * 修改一条记录
     * @param category
     */
    void update(Category category);

    /**
     * 获取一条记录
     * @param id
     * @return
     */
    Category get(int id);

}
