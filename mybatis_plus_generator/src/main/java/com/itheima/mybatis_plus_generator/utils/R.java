package com.itheima.mybatis_plus_generator.utils;

import com.itheima.mybatis_plus_generator.enums.FlagEnum;
import lombok.Data;



/**
 * 统一输出格式给前端
 */

@Data
public class R {
    private String status;
    private String msg;
    private Object data;

    public R() {
    }

    public R(String msg) {
        this.msg = msg;
    }

    public R(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public R(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public R(String status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 通用返回成功
     */
    public static R ok(){
        R r=new R();
        r.setStatus(FlagEnum.SUCCESS.getStatus());
        r.setMsg(FlagEnum.SUCCESS.getMessage());
        return r;
    }


    /**
     * 通用返回失败，未知错误
     *
     * @return
     */
    public static R error(){
        R r=new R();
        r.setStatus(FlagEnum.ERROR.getStatus());
        r.setMsg(FlagEnum.ERROR.getMessage());
        return r;
    }

    public R data(Object obj) {
        this.setData(obj);
        return this;
    }
}
