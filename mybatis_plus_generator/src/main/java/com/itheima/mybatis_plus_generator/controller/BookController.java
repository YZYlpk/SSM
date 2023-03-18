package com.itheima.mybatis_plus_generator.controller;



import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itheima.mybatis_plus_generator.entity.Book;
import com.itheima.mybatis_plus_generator.enums.FlagEnum;
import com.itheima.mybatis_plus_generator.exception.GlobalException;
import com.itheima.mybatis_plus_generator.service.IBookService;
import com.itheima.mybatis_plus_generator.service.impl.BookServiceImpl;


import com.itheima.mybatis_plus_generator.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiPeiKai
 * @since 2023-03-08
 */
@Slf4j
@RestController
@RequestMapping("/books")
public class BookController {
    @Value("${spring.datasource.driver-class-name}")
    String drivername;

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;

    @Resource
    BookServiceImpl bookService;

    @Resource
    IBookService iBookService;

    //创建记录日志的对象
    //private static final Logger log = LoggerFactory.getLogger(BookController.class);


    //输出数据库相关配置
    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id){
        System.out.println("id ==> "+id);
        System.out.println(drivername);
        System.out.println(url);
        System.out.println(username);
        System.out.println(password);
        return "hello , spring boot!";
    }


    //查询所有数据并统计数量
    @PostMapping("/select")
    public  R selectAll(){
        Map<String,Object> rsp=bookService.select();
        //用枚举类输出返回数据格式
        return new R(FlagEnum.SELECT_SUCCESS.getStatus(), FlagEnum.SELECT_SUCCESS.getMessage(),rsp);
    }

    //查询所有数据并按类型分类
    @RequestMapping("/selectByType")
    public R selectByType() throws GlobalException {
        List<Map<String,Object>> rsp=bookService.selectByType();
        //用枚举类输出返回数据格式
        return new R(FlagEnum.SELECT_SUCCESS.getStatus(), FlagEnum.SELECT_SUCCESS.getMessage(),rsp);
    }
    //查询一个
    @PostMapping("/selectone")
    public R selectOne(@RequestParam("id") Integer id) throws GlobalException {
        Book rsp=bookService.selectOne(id);
        //用枚举类输出返回数据格式
        return new R(FlagEnum.SELECT_SUCCESS.getStatus(), FlagEnum.SELECT_SUCCESS.getMessage(),rsp);
    }


    /** ----------------------------------------------------------------- **/
    //分页+模糊查询
    @GetMapping("{currentPage}/{pageSize}")
    public R getPage(@PathVariable int currentPage, @PathVariable int pageSize, Book book) {
        IPage<Book> page = iBookService.getPage(currentPage, pageSize, book);
        // 如果当前页面值大于总页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if (currentPage > page.getPages()) {
            page = iBookService.getPage((int) page.getPages(), pageSize, book);
        }
        return  R.ok().data(page);
    }


    //更新1（mybatis-dao层用sql处理）
    @RequestMapping(value = "/update1")
    public R update1(@RequestBody Map<String,Object> req){
        Book req1= JSON.parseObject(JSON.toJSONString(req.get("Book")),Book.class);
        //更新图书
        if(bookService.update1(req1)!=0){
            //查询更新后的图书
            Book rsp=bookService.selectOne(req1.getId());
            return new R(FlagEnum.UPDATE_SUCCESS.getStatus(), FlagEnum.UPDATE_SUCCESS.getMessage(),rsp);
        }
        else
            return new R(FlagEnum.UPDATE_ERROR.getStatus(), FlagEnum.UPDATE_ERROR.getMessage(),null);//用枚举类输出返回数据格式
    }

    //更新2（MP自带的updateById方法）
    @RequestMapping(value = "/update2")
    public R update2(@RequestBody Map<String,Object> req) throws GlobalException {
        Book req1= JSON.parseObject(JSON.toJSONString(req.get("Book")),Book.class);
        //更新图书
        bookService.update2(req1);
        //查询更新后的图书
        Book rsp=bookService.selectOne(req1.getId());
        return R.ok().data(rsp);//用R的静态方法ok（）输出通用成功格式,用GlobalException抛异常输出通用失败格式
    }

    //更新3（MP自带的update方法）
    @RequestMapping(value = "/update3")
    public R update3(@RequestBody Map<String,Object> req) throws GlobalException {
        Book req1= JSON.parseObject(JSON.toJSONString(req.get("Book")),Book.class);
        //更新图书
        bookService.update3(req1);
        //查询更新后的图书
        Book rsp=bookService.selectOne(req1.getId());
        return R.ok().data(rsp);//用R的静态方法ok（）输出通用格式,用GlobalException抛异常输出通用失败格式
    }

    //新增图书1（mybatis-dao层用sql处理）
    @RequestMapping(value = "/insert1")
    public R insert1(@RequestBody Map<String,Object> req){
        Book req1= JSON.parseObject(JSON.toJSONString(req.get("Book")),Book.class);
        //插入新数据
        if(bookService.insert1(req1)!=0){
            return new R(FlagEnum.INSERT_SUCCESS.getStatus(), FlagEnum.INSERT_SUCCESS.getMessage());
        }
        else
            return new R(FlagEnum.INSERT_ERROR.getStatus(), FlagEnum.INSERT_ERROR.getMessage());//用枚举类输出返回数据格式

    }

    //新增图书2 (mybatis自带的insert方法)
    @RequestMapping(value = "/insert2")
    public R insert2(@RequestBody Map<String,Object> req) throws GlobalException {
        Book req1= JSON.parseObject(JSON.toJSONString(req.get("Book")),Book.class);
        //插入新数据
        bookService.insert2(req1);
        //查询新增的数据 即使req没给id，用了MP自带的方法插入后会自动给req的id赋值，所以可以用selectOne方法通过id查询
        Book rsp=bookService.selectOne(req1.getId());
        return R.ok().data(rsp);//用R的静态方法ok（）输出通用格式,用GlobalException抛异常输出通用失败格式
    }


    //删除图书 (mybatis-dao层用sql处理)
    @RequestMapping("/delete1")
    public R delete1(@RequestParam(value = "id") Integer id) throws GlobalException {
        bookService.delete1(id);
        return R.ok();
    }


    //删除图书 (MP自带的deleteById方法)
    @RequestMapping("/delete2")
    public R delete2(@RequestParam("id") Integer id) throws GlobalException {
        bookService.delete2(id);
        return R.ok();
    }

    /** ----------------------------------------------------------------- **/
    @DeleteMapping("{id}")
    public R delete(@PathVariable Integer id) {
        boolean flag = iBookService.removeById(id);
        if(flag) {
            String str = "删除成功^_^";
            return new R(FlagEnum.SUCCESS.getStatus(),str);
        }
        else {
            String str = "删除失败-_-!";
            return new R(FlagEnum.ERROR.getStatus(),str);
        }
    }
}

