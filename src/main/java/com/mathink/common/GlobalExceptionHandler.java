package com.mathink.common;

import com.mathink.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * 全局异常处理器
 * 统一处理 Controller 层抛出的异常,返回标准 Result 格式
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义业务异常
     *
     * @param e 业务异常
     * @return 统一响应结果
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        log.error("业务异常: {}", e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 处理参数校验异常
     *
     * @param e 参数校验异常
     * @return 统一响应结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("参数校验失败");
        log.error("参数校验异常: {}", message);
        return Result.error(message);
    }

    /**
     * 处理GET请求参数校验异常
     *
     * @param e 参数绑定异常
     * @return 统一响应结果
     */
    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("参数校验失败");
        log.error("参数校验异常: {}", message);
        return Result.error(message);
    }

    /**
     * 处理未知异常(兜底)
     *
     * @param e 未知异常
     * @return 统一响应结果
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统异常: ", e);
        return Result.error("系统繁忙,请稍后重试");
    }
}
