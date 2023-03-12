package com.itheima.mybatis_plus_generator.controller;



import com.alibaba.fastjson.JSON;
import com.itheima.mybatis_plus_generator.entity.Book;
import com.itheima.mybatis_plus_generator.enums.FlagEnum;
import com.itheima.mybatis_plus_generator.service.impl.BookServiceImpl;


import com.itheima.mybatis_plus_generator.utils.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
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
@RestController
@RequestMapping("/book")
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
    public R selectByType(){
        List<Map<String,Object>> rsp=bookService.selectByType();
        //用枚举类输出返回数据格式
        return new R(FlagEnum.SELECT_SUCCESS.getStatus(), FlagEnum.SELECT_SUCCESS.getMessage(),rsp);
    }
    //查询一个
    @PostMapping("/selectone")
    public R selectOne(@RequestParam("id") Integer id){
        Book rsp=bookService.selectOne(id);
        //用枚举类输出返回数据格式
        return new R(FlagEnum.SELECT_SUCCESS.getStatus(), FlagEnum.SELECT_SUCCESS.getMessage(),rsp);
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
    public R update2(@RequestBody Map<String,Object> req){
        Book req1= JSON.parseObject(JSON.toJSONString(req.get("Book")),Book.class);
        //更新图书
        String str=bookService.update2(req1);
        System.err.println(str);
        //查询更新后的图书
        Book rsp=bookService.selectOne(req1.getId());
        Map<String,Object> map=new HashMap<>();
        map.put(str,rsp);
        return R.ok().data(rsp);//用R的静态方法ok（）输出通用格式
    }

    //更新3（MP自带的update方法）
    @RequestMapping(value = "/update3")
    public R update3(@RequestBody Map<String,Object> req){
        Book req1= JSON.parseObject(JSON.toJSONString(req.get("Book")),Book.class);
        //更新图书
        String str=bookService.update3(req1);
        System.err.println(str);
        //查询更新后的图书
        Book rsp=bookService.selectOne(req1.getId());
        Map<String,Object> map=new HashMap<>();
        map.put(str,rsp);
        return R.ok().data(rsp);//用R的静态方法ok（）输出通用格式
    }

    //新增图书1（mybatis-dao层用sql处理）
    @RequestMapping(value = "/insert1")
    public R insert1(@RequestBody Map<String,Object> req){
        Book req1= JSON.parseObject(JSON.toJSONString(req.get("Book")),Book.class);
        //插入新数据
        if(bookService.insert1(req1)!=0){
            //查询新增的数据
            Book rsp=bookService.selectOne(req1.getId());
            return new R(FlagEnum.INSERT_SUCCESS.getStatus(), FlagEnum.INSERT_SUCCESS.getMessage(),rsp);
        }
        else
            return new R(FlagEnum.INSERT_ERROR.getStatus(), FlagEnum.INSERT_ERROR.getMessage(),null);//用枚举类输出返回数据格式

    }

    //新增图书2 (mybatis自带的insert方法)
    @RequestMapping(value = "/insert2")
    public R insert2(@RequestBody Map<String,Object> req){
        Book req1= JSON.parseObject(JSON.toJSONString(req.get("Book")),Book.class);
        //插入新数据
        String str=bookService.insert2(req1);
        //查询新增的数据
        Book rsp=bookService.selectOne(req1.getId());
        Map<String,Object> map=new HashMap<>();
        map.put(str,rsp);
        return R.ok().data(rsp);//用R的静态方法ok（）输出通用格式
    }

    //删除图书
}

