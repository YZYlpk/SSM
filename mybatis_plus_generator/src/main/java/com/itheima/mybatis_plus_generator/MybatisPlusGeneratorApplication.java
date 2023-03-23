package com.itheima.mybatis_plus_generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.sql.SQLOutput;

@SpringBootApplication
@MapperScan("com.itheima.mybatis_plus_generator.dao") //mapper接口位置，在这里加就不需要在每个mapper接口类上面加@mapper注解
//开启缓存功能
@EnableCaching
public class MybatisPlusGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusGeneratorApplication.class, args);
    }

}
