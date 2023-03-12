package com.itheima.mybatis_plus_generator.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.beans.Transient;
import java.io.Serializable;

import lombok.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author LiPeiKai
 * @since 2023-03-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tbl_book")
@Builder
//上面有@Builder和@Data，默认的构造函数因为@Builder而丢失。因此需要加下面两个注解，这样就可以有有参构造函数和无参构造函数了
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String type;

    private String name;

    private String description;

//    //非数据库字段，用来计数
//    @TableField(exist = false)
//    private Integer count;



}
