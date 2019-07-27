package com.finstone.tmall.controller;

import com.finstone.tmall.entity.Category;
import com.finstone.tmall.entity.Page;
import com.finstone.tmall.service.CategoryService;
import com.finstone.tmall.util.ImageUtil;
import com.finstone.tmall.util.UploadImageFile;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

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

    //后台管理入口
    @RequestMapping("admin")
    public String admin(Model model){
        return "redirect:admin_category_list";
    }

    @RequestMapping("admin_category_list")
    public String list(Model model, Page page){
        //指定分页参数
        PageHelper.offsetPage(page.getStart(), page.getCount());//param1起始位置，param2偏移量（每页大小）。
        //紧跟在PageHelper插件后，获取分页数据，线程安全。https://www.cnblogs.com/ljdblog/p/6725094.html
        List<Category> cs = categoryService.list();
        //通过PageInfo获取总数
        int total = (int) new PageInfo<>(cs).getTotal();
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
            BufferedImage img = ImageUtil.change2jpg(file);//转换图片（数据存储）格式为jpg
            ImageIO.write(img, "jpg", file); //保存图片到文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:admin_category_list";
    }

    /**
     * 页面跳转 to 分类修改页
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("admin_category_edit")
    public String edit(Model model, int id){
        Category category = categoryService.get(id);
        model.addAttribute("c",category);
        return "admin/editCategory";
    }

    /**
     * 修改分类
     * @param c 接收页面提交的分类名称 POJO
     * @param session 用于获取当前应用的路径 for 静态资源路径
     * @param uploadImageFile 接收上传的图片（如果有上传，则保存，覆盖本地文件）
     * @return
     */
    @RequestMapping("admin_category_update")
    public String update(Category c, HttpSession session, UploadImageFile uploadImageFile){
        //1.更新分类
        categoryService.update(c);

        //2.更新图片（如果有上传文件）
        MultipartFile image =  uploadImageFile.getImage();
        if(null!=image && !image.isEmpty()){//判断文件是否上传
            File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
            File file = new File(imageFolder, c.getId() + ".jpg");//创建图片对象
            try {
                //接收上传图片
                image.transferTo(file);
                //转换图片（数据存储）格式为jpg
                BufferedImage bufferedImage = ImageUtil.change2jpg(file);
                //保存图片到文件
                ImageIO.write(bufferedImage, "jpg", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:admin_category_list";
    }

    /**
     * 删除分类，并删除图片文件
     * @param id 接收页面提交的分类名称 POJO
     * @param session 用于获取当前应用的路径 for 静态资源路径
     * @return
     */
    @RequestMapping("admin_category_delete")
    public String delete(int id, HttpSession session){
        //1.删除记录
        categoryService.delete(id);

        //2.删除图片文件
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, id + ".jpg");
        if(file.exists()){
            file.delete();
        }
        return "redirect:admin_category_list";
    }

}