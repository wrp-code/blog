package com.wrp.blog.common.exception;


import cn.hutool.json.JSONUtil;
import com.wrp.blog.common.enums.ResultType;
import com.wrp.blog.common.result.Result;
import com.wrp.blog.common.result.ResultUtils;
import com.wrp.blog.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * 全局异常处理
 * @author wrp
 * @date 2024年08月17日 15:16
 * @since 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class BusinessControllerAdvice {

    @ExceptionHandler(NoResourceFoundException.class)
    public Result<String> handleNoResourceFoundException(NoResourceFoundException e) {
        return ResultUtils.error(ResultType.S_NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResultUtils.error(JSONUtil.toJsonStr(e.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList()));
    }

    @ExceptionHandler(BusinessException.class)
    public Result<String> handleBusinessException(BusinessException e) {
        return ResultUtils.error(e.getResultType());
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handle(Exception e) {
        log.error("异常信息：", e);
        return ResultUtils.error(ResultType.FAIL);
    }
}
