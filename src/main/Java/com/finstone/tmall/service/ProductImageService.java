package com.finstone.tmall.service;

import com.finstone.tmall.entity.Product;
import com.finstone.tmall.entity.ProductImage;

import java.util.List;

public interface ProductImageService {
    String type_single = "type_single";
    String type_detail = "type_detail";

    void add(ProductImage productImage);

    void delete(int id);

    void update(ProductImage productImage);

    ProductImage get(int id);

    /**
     * 根据产品编号pid和图片类型（缩略图 or 详情）
     * @param pid
     * @param type
     * @return
     */
    List<ProductImage> list(int pid, String type);
}
