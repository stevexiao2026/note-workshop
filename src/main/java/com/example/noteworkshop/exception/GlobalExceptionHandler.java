package com.example.noteworkshop.exception;

import com.example.noteworkshop.common.result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public result<?> handleBusinessException(BusinessException e) {
        return result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public result<?> handleException(Exception e) {
        return result.error(500, "系统异常：" + e.getMessage());
    }
}