package com.itheima.mybatis_plus_generator.dto;

import lombok.Data;

@Data
public class SMSCodeDto {
    //手机号码
    private String phone;
    //验证码
    private String code;
}
