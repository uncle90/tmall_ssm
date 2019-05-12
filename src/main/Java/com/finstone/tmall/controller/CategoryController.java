package com.finstone.tmall.controller;

import com.finstone.tmall.entity.Category;
import com.finstone.tmall.entity.Page;
import com.finstone.tmall.service.CategoryService;
import com.finstone.tmall.util.ImageUtil;
import com.finstone.tmall.util.UploadImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_category_list")
    public String list(Model model, Page page){
        List<Category> cs = categoryService.list(page);
        int total = categoryService.total();
        page.setTotal(total);
        model.addAttribute("cs",cs);
        model.addAttribute("page", page);
        return "admin/listCategory";
    }

    /**
     * 新增分类，并保存分类图片（到img/category）
     * @param c 接收页面提交的分类名称 POJO
     * @param session 用于获取当前应用的路径 for 静态资源路径
     * @param uploadImageFile 接收上传的图片
     * @return
     */
    @RequestMapping("admin_category_add")
    public String add(Category c, HttpSession session, UploadImageFile uploadImageFile){
        //1.新增分类
        categoryService.add(c);

        //2.保存图片
        File imageFolder = new File(session.getServletContext().getRealPath("img/category")); //或 request.getSession().getServletContext().getRealPath("/")
        File file = new File(imageFolder, c.getId()+".jpg"); //创建图片对象
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        try {
            uploadImageFile.getImage().transferTo(file);   //接收上传图片
            BufferedImage img = ImageUtil.change2jpg(file);//转换图片格式为jpg
            ImageIO.write(img, "jpg", file); //保存图片到文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin_category_list";
    }
}