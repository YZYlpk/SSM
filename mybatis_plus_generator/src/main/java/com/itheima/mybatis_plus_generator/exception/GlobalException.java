package com.itheima.mybatis_plus_generator.exception;

import com.itheima.mybatis_plus_generator.utils.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException extends Throwable {
    /**-------- 通用异常处理方法 --------**/
    @ExceptionHandler
    public R doException(Exception ex) {
        // 记录日志
        // 通知运维
        // 通知开发
        ex.printStackTrace();
        return R.error();
    }
}
