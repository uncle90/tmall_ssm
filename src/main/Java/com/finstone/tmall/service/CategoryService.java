package com.finstone.tmall.service;

import com.finstone.tmall.entity.Category;
import com.finstone.tmall.entity.Page;

import java.util.List;

public interface CategoryService {
    /**
     * 获取记录总数
     * @return
     */
    int total();

    /**
     * （分页）获取所有记录
     * @param page
     * @return
     */
    List<Category> list(Page page);
}
