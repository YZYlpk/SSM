package com.itheima.mybatis_plus_generator.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mybatis_plus_generator.dto.BookDto;
import com.itheima.mybatis_plus_generator.entity.Book;
import com.itheima.mybatis_plus_generator.dao.BookDao;
import com.itheima.mybatis_plus_generator.exception.GlobalException;
import com.itheima.mybatis_plus_generator.service.IBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiPeiKai
 * @since 2023-03-08
 */
@Slf4j
@Service
public class BookServiceImpl extends ServiceImpl<BookDao, Book> implements IBookService {
    @Resource
    BookDao bookDao;

    //添加日志记录操作,也可以直接加注解@Slf4j
    //private static final Logger log= LoggerFactory.getLogger(BookServiceImpl.class);

    public  Map<String,Object> select(){
        Map<String,Object> map=new LinkedHashMap<>();
        Integer count =bookDao.count1();
        map.put("图书数量",count);
        List<Book> rsp= bookDao.select();
        map.put("图书数据",rsp);
        return map;
    }

    public List<Map<String,Object>> selectByType() throws GlobalException {
        List<Book> rsp = bookDao.select();
        List<Map<String, Object>> mapList = null;
        if (rsp.isEmpty())
        {
            //System.out.println("数据为空");
            log.error("数据为空");
            throw new GlobalException();
        }
        else {
            mapList = new ArrayList<>();
            //取出所有去重后的type，放到Set集合
            Set<String> set = rsp.stream().map(e -> e.getType()).collect(Collectors.toSet());
            //遍历Set集合中所有的type
            for (String type : set) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("类型", type);
                //利用stream流的过滤和映射方法，筛选出符合条件的对象并映射成对应的DTO对象
                List<BookDto> listBookDto = rsp.stream().filter(e -> type.equals(e.getType())).map(e -> {
                    BookDto bookDto = new BookDto();
                    //spring框架下beans包下的工具类：beanutils.copyProperties方法拷贝复制相同类型和相同名称的属性
                    BeanUtils.copyProperties(e, bookDto);
                    return bookDto;
                }).collect(Collectors.toList());//将收集到的信息存储到一个List集合
                map.put("数量",listBookDto.size());
                map.put("数据", listBookDto);
                mapList.add(map);
            }
        }
        return mapList;
    }

    public Book selectOne(Integer id){
        Book rsq= bookDao.selectOne(id);
        return rsq;
    }

    public Integer update1(Book req){
        return bookDao.update1(req.getId(),req.getType(),req.getName(),req.getDescription());
    }

    public void update2(Book req) throws GlobalException {
        String str=null;
        Book book= Book.builder()
                .id(req.getId())
                .name(req.getName())
                .type(req.getType())
                .description(req.getDescription())
                .build();
        //根据id修改其他字段
        Integer result=bookDao.updateById(book);
        if(result.equals(1)) {
            str = "数据更新成功";
            //System.out.println(str);
            log.info(str);
        }
        else if (result.equals(0)) {
            str = "数据更新失败";
            //System.out.println(str);
            log.error(str);
            throw new GlobalException();
        }
    }
    public void update3(Book req) throws GlobalException {
        String str=null;
        Book book= Book.builder()
                .name(req.getName())
                .type(req.getType())
                .description(req.getDescription())
                .build();

        UpdateWrapper<Book> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",req.getId());


        Integer result=bookDao.update(book,updateWrapper);//第一个参数为set条件，第二个参数为where条件。
        if(result.equals(1)) {
            str = "数据更新成功";
            //System.out.println(str);
            log.info(str);
        }
            else if (result.equals(0)) {
            str = "数据更新失败";
            //System.out.println(str);
            log.error(str);
            throw new GlobalException();
        }
    }

    public Integer insert1(Book req){
        if(bookDao.selectOne(req.getId())!=null && !bookDao.selectOne(req.getId()).equals(""))
        {
            //System.err.println("id已被占用，数据插入失败");
            log.error("id已被占用，数据插入失败");
            return 0;
        }
        else
        {
            Integer integer = bookDao.insert1(req.getId(), req.getType(), req.getName(), req.getDescription());
            System.out.println(integer);
            //System.err.println("已插入数据");
            log.info("已插入数据");
            return 1;
        }

    }

    public void insert2(Book req) throws GlobalException {
        String str=null;
        if(bookDao.selectOne(req.getId())!=null && !bookDao.selectOne(req.getId()).equals("")){
            str="id已被占用，数据插入失败";
            //System.err.println(str);
            log.error(str);
            throw new GlobalException();
        }
        else
        {
            /**   MP自带的方法，插入数据没给id时，使用insert方法后，自动给req的id赋值  **/
            //System.out.println(req.getId());
            bookDao.insert(req);
            //System.out.println(req.getId());
            str="已插入数据";
            //System.err.println(str);
            log.info(str);
        }
    }

    public void delete1(Integer id) throws GlobalException {
        //先判断是否有对应id的数据
        if(bookDao.selectOne(id)==null || bookDao.selectOne(id).equals(""))
            throw new GlobalException();
        else
            bookDao.delete1(id);
    }

    public void delete2(Integer id) throws GlobalException {
        //先判断是否有对应id的数据
        if(bookDao.selectOne(id)==null || bookDao.selectOne(id).equals(""))
            throw new GlobalException();
        else
            bookDao.deleteById(id);
    }

    @Override
    public IPage<Book> getPage(int currentPage, int pageSize) {
        IPage<Book> page = new Page<>(currentPage, pageSize);
        bookDao.selectPage(page, null);
        return page;
    }

    @Override
    public IPage<Book> getPage(int currentPage, int pageSize, Book book) {
        IPage<Book> page = new Page<>(currentPage, pageSize);
        //模糊查询
        LambdaQueryWrapper<Book> lqw = new LambdaQueryWrapper<>();
        lqw.like(Strings.isNotEmpty(book.getName()), Book::getName, book.getName());
        lqw.like(Strings.isNotEmpty(book.getType()), Book::getType, book.getType());
        lqw.like(Strings.isNotEmpty(book.getDescription()), Book::getDescription, book.getDescription());
        bookDao.selectPage(page, lqw);
        return page;
    }
}
