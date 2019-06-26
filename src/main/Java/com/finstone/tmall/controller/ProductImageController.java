package com.finstone.tmall.controller;

import com.finstone.tmall.entity.Product;
import com.finstone.tmall.entity.ProductImage;
import com.finstone.tmall.service.ProductImageService;
import com.finstone.tmall.service.ProductService;
import com.finstone.tmall.util.ImageUtil;
import com.finstone.tmall.util.UploadImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 注意：
 * 以下方法全部针对某个特定的分类 cid
 */
@Controller
@RequestMapping("")
public class ProductImageController {

    @Autowired
    ProductImageService productImageService;

    @Autowired
    ProductService productService;

    /**
     * 查询指定产品pid的图片——区分类型：概况图、详情图
     * @param model
     * @param pid
     * @return
     */
    @RequestMapping("admin_productImage_list")
    public  String list(Model model, int pid){
        Product product = productService.get(pid);
        //单个（类）图片
        List<ProductImage> pisSingle = productImageService.list(pid, "type_single");
        //详情（类）图片
        List<ProductImage> pisDetail = productImageService.list(pid, "type_detail");

        model.addAttribute("p",product);
        model.addAttribute("pisSingle", pisSingle);
        model.addAttribute("pisDetail", pisDetail);
        return "admin/listProductImage";
    }

    /**
     * 给指定产品pid添加图片——区分类型：概况图、详情图
     * @param pi
     * @param session 用于获取当前应用的路径 for 静态资源路径
     * @param uploadImageFile 接收上传的图片
     * @return
     */
    @RequestMapping("admin_productImage_add")
    public String add(ProductImage pi, HttpSession session, UploadImageFile uploadImageFile) throws Exception {
        //1.新增产品图片
        productImageService.add(pi);

        //2.保存图片
        String fileName = pi.getId()+".jpg";
        //创建保存对象（目录）
        String imageFolder;
        String imageFolder_middle = null;
        String imageFolder_small  = null;
        if(ProductImageService.type_single.equals(pi.getType())){
            imageFolder = session.getServletContext().getRealPath("img/productSingle");
            imageFolder_middle = session.getServletContext().getRealPath("img/productSingle_middle");
            imageFolder_small  = session.getServletContext().getRealPath("img/productSingle_small");
        }else if(ProductImageService.type_detail.equals(pi.getType())){
            imageFolder = session.getServletContext().getRealPath("img/productDetail");
        }else{
            throw new Exception("图片分类错误");
        }
        //创建目录
        File file = new File(imageFolder, fileName);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        //接收图片、转换格式、压缩大小
        try {
            uploadImageFile.getImage().transferTo(file);    //接收图片
            BufferedImage img = ImageUtil.change2jpg(file); //转换格式
            ImageIO.write(img,"jpg", file);   //原大小保存
            if(ProductImageService.type_single.equals(pi.getType())){
                File file_middle = new File(imageFolder_middle, fileName);
                File file_small  = new File(imageFolder_small, fileName);
                ImageUtil.resizeImage(file, 56, 56, file_middle);
                ImageUtil.resizeImage(file, 217, 190, file_small);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //3.重定向
        return "redirect:admin_productImage_list?pid="+pi.getPid();
    }


    /**
     * 删除图片和记录
     * @param id
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("admin_productImage_delete")
    public String delete(int id, HttpSession session) throws Exception {
        //1.查询图片类型
        ProductImage pi = productImageService.get(id);

        //2.删除文件
        String fileName = pi.getId()+".jpg";
        File file;
        File file_middle = null;
        File file_small  = null;
        if("type_single".equals(pi.getType())){
            file = new File(session.getServletContext().getRealPath("img/productSingle"), fileName);
            file_middle = new File(session.getServletContext().getRealPath("img/productSingle_middle"), fileName);
            file_small  = new File(session.getServletContext().getRealPath("img/productSingle_small") , fileName);
            file.delete();
            file_middle.delete();
            file_small.delete();
        }else if("type_detail".equals(pi.getType())){
            file = new File(session.getServletContext().getRealPath("img/productDetail"), fileName);
            file.delete();
        }else{
            throw new Exception("图片类型错误");
        }

        //3.删除记录
        productImageService.delete(id);

        //4.重定向
        return "redirect:admin_productImage_list?pid="+pi.getPid();
    }
}