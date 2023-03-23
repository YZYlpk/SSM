package com.itheima.mybatis_plus_generator.controller;

import com.itheima.mybatis_plus_generator.dto.SMSCodeDto;
import com.itheima.mybatis_plus_generator.exception.GlobalException;
import com.itheima.mybatis_plus_generator.service.impl.SMSCodeServiceImpl;
import com.itheima.mybatis_plus_generator.utils.R;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sms")
public class SMSCodeController {
    @Resource
    private SMSCodeServiceImpl smsCodeService;

    //获取手机验证码
    @GetMapping
    public R getCode(@Param("phone") String phone){
        String code = smsCodeService.sendCodeToSMS(phone);
        return R.ok().data(code);
    }

    //验证码校验
    @PostMapping
    public R checkCode(SMSCodeDto smsCode) throws GlobalException {
        return R.ok().data(smsCodeService.checkCode(smsCode));
    }
}
