package com.itheima.mybatis_plus_generator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itheima.mybatis_plus_generator.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiPeiKai
 * @since 2023-03-08
 */
public interface IBookService extends IService<Book> {
    IPage<Book> getPage(int currentPage, int pageSize);
    IPage<Book> getPage(int currentPage, int pageSize, Book book);
}
