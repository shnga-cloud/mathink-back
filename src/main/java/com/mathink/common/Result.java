package com.mathink.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 统一响应结果封装类
 * @param <T> 数据类型
 */
@Data
@AllArgsConstructor
public class Result<T> {

    /**
     * 状态码: 200-成功, 其他-失败
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 成功(无数据)
     */
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    /**
     * 成功(带数据)
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    /**
     * 失败(自定义消息)
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(500, message, null);
    }

}
