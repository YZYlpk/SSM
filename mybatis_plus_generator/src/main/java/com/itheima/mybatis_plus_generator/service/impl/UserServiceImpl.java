package com.itheima.mybatis_plus_generator.service.impl;

import com.itheima.mybatis_plus_generator.entity.User;
import com.itheima.mybatis_plus_generator.dao.UserDao;
import com.itheima.mybatis_plus_generator.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiPeiKai
 * @since 2023-03-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {

}
