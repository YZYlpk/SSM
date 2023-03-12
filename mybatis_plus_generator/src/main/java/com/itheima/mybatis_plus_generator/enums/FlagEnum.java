package com.itheima.mybatis_plus_generator.enums;

import lombok.Getter;

/**
 * 枚举
 */

@Getter
public enum FlagEnum {
    //通用输出格式
    SUCCESS("success","成功"),
    ERROR("error","查询失败"),

    //详细输出格式
    SELECT_SUCCESS("success","查询成功"),
    SELECT_ERROR("error","查询失败"),
    INSERT_SUCCESS("success","插入成功"),
    INSERT_ERROR("error","插入失败"),
    UPDATE_SUCCESS("success","更新成功"),
    UPDATE_ERROR("error","更新失败"),
    DELETE_SUCCESS("success","删除成功"),
    DELETE_ERROR("error","删除失败");

    /**
     * 响应是否成功
     */
    private String status;
    /**
     * 响应信息
     */
    private String message;

    FlagEnum(String status, String message) {
        this.status=status;
        this.message=message;
    }
}
