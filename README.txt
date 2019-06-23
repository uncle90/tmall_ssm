1、Mybatis分页插件ageHelper
1）引入jar包；
2）在mybatis-config.xml或applicationContext.xml中配置分页插件；
3）PojoMapper.xml中删除手工分页内容：查total方法和分页sql；
4）PojoMapper.java中删除total()和list(Page page)，新增list()；
5）PojoService 和 PojoServiceImpl 同理；


2、逆向工程
逆向工程，就是在已经存在的数据库表结构基础上，通过工具，自动生成Category.java,
CategoryMapper.java和CategoryMapper.xml。

3、后台管理为什么用重定向
服务端接收到用户请求后，经过处理、修改用户请求，让用户被动地使用新的请求。
重定向一般是为了防止用户提交完数据后点浏览器刷新或点后退之后产生重复提交。
3.1 重定向的三种方式
https://zhidao.baidu.com/question/538384198.html
https://www.jb51.net/article/111706.htm
(1) 通过ModelAndView跳转。              return response.sendRedirect("admin_productImage_list");
(2) 通过HttpServletResponse跳转。       return new ModelAndView("/admin_productImage_list");
(3) 通过redirect返回String类型跳转。    return "redirect:/admin_productImage_list";
注意：第三种方法不允许Spring控制器用@RestController注解，因为@RestController相当于类中的所有方法都标注了@ResponseBody，
这些方法不会返回一个视图，而是返回一个json对象。这样的话只是在页面上打印出字符串，而不跳转。控制器用@Controller注解即可。
