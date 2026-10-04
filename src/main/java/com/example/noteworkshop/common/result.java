package com.example.noteworkshop.common;

import lombok.Data;

@Data
public class result<T> {
    private Integer code;
    private String msg;
    private T data;

    private result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> result<T> success(T data) {
        return new result<>(200, "success", data);
    }

    public static <T> result<T> success() {
        return new result<>(200, "success", null);
    }

    public static <T> result<T> error(Integer code, String msg) {
        return new result<>(code, msg, null);
    }

    public static <T> result<T> error(String msg) {
        return new result<>(500, msg, null);
    }
}