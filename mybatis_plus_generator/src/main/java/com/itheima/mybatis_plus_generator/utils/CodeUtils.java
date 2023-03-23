package com.itheima.mybatis_plus_generator.utils;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
/**
 * @Author lpk
 * @Date 2023/03/23
 * @Description 生成验证码的工具类，位数少于6位左边填充0
 */
@Component
public class CodeUtils {

    private final String flag="000000";
    // 生成验证码（位数少于6位左边填充0，填充方法1）
    public String generator(String phone){
        int hash = phone.hashCode();//生成哈希码
        int encryption= 20236666;//加密码
        long result = hash ^ encryption;//加密：异或运算
        long nowTime=System.currentTimeMillis();
        result=result ^ nowTime;//二次加密：和当前时间做异或运算
        long code1=result % 1000000;//求余取六位
        code1=Math.abs(code1);//取绝对值
        String code=code1+"";//整型转为字符串
        code=flag.substring(code.length())+code;//位数少于6位左边填充0
        return code;
    }

    private final String[] patch = {"000000", "00000", "0000", "000", "00", "0", ""};
    // 生成验证码（位数少于6位左边填充0，填充方法2）
    public String generator1(String phone){
        int hash = phone.hashCode();
        int encryption= 20236666;//加密码
        long result = hash ^ encryption;//加密：异或运算
        long nowTime=System.currentTimeMillis();
        result=result ^ nowTime;//二次加密：和当前时间做异或运算
        long code1=result % 1000000;//求余取六位
        code1=Math.abs(code1);//取绝对值
        String code=code1+"";//整型转为字符串
        code=patch[code.length()]+code;//位数少于6位左边填充0
        return code;
    }

    // 根据手机号从缓存中获取验证码，缓存中有的话返回缓存中的值，没有的话就返回null
    @Cacheable(value = "cacheSpace", key = "#phone")
    public String getCodeByPhoneFromCache(String phone) {
        return null;
    }
}
