package com.itheima.mybatis_plus_generator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.mybatis_plus_generator.entity.Book;


import org.apache.ibatis.annotations.Param;



import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiPeiKai
 * @since 2023-03-08
 */


public interface BookDao extends BaseMapper<Book> {

    List<Book> select();

    Integer count1();
    Book selectOne(@Param("id") Integer id);

    //更新，删除，插入不需要返回数据类型，都是默认返回一个int，所以返回类型用Integer
    Integer update1(@Param("id") Integer id,@Param("type") String type,@Param("name")String name,@Param("description") String description);

    Integer insert1(@Param("id") Integer id,@Param("type") String type,@Param("name")String name,@Param("description") String description);

    Integer delete1(@Param("id") Integer id);
}
