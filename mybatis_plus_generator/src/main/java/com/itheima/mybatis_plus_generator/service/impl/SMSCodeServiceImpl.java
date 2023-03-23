package com.itheima.mybatis_plus_generator.service.impl;

import com.itheima.mybatis_plus_generator.dto.SMSCodeDto;
import com.itheima.mybatis_plus_generator.exception.GlobalException;
import com.itheima.mybatis_plus_generator.utils.CodeUtils;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class SMSCodeServiceImpl {

    @Resource
    private CodeUtils codeUtils;

    // @Cacheable(value = "cacheSpace", key = "#phone")
    // 这里@Cacheable注解不适用，因为@Cacheable注解的功能是：如果缓存中没有值就去执行一次方法，然后将值存到缓存中，
    // 如果有值就直接从缓存中取值并返回，并不会执行方法，因而缓存中值不会发生改变。
    // 而用户点击界面每发送一次验证码就调用了一次该方法，每次得到新的验证码应该是不同的。
    // 使用新的缓存注解@CachePut可以解决这个问题，每次调用都会执行方法，向缓存中存新的值并返回
    // 该注解只存只更新但不取，根据手机号向缓存中更存入或者更新最新的验证码
    @CachePut(value = "cacheSpace", key = "#phone")
    public String sendCodeToSMS(String phone){
        String code=codeUtils.generator1(phone);
        return code;
    }

    public boolean checkCode(SMSCodeDto smsCode) throws GlobalException {
        //取出内存中的验证码与传递过来的验证码对比
        String cacheCode = codeUtils.getCodeByPhoneFromCache(smsCode.getPhone());
        String code=smsCode.getCode();
        if(code.equals(cacheCode))
            return true;
        else
            throw new GlobalException();
    }
}
