1、Mybatis分页插件ageHelper
1）引入jar包；
2）在mybatis-config.xml或applicationContext.xml中配置分页插件；
3）PojoMapper.xml中删除手工分页内容：查total方法和分页sql；
4）PojoMapper.java中删除total()和list(Page page)，新增list()；
5）PojoService 和 PojoServiceImpl 同理；


2、逆向工程
逆向工程，就是在已经存在的数据库表结构基础上，通过工具，自动生成Category.java,
CategoryMapper.java和CategoryMapper.xml。