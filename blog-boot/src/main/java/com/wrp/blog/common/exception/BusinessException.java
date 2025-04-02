package com.wrp.blog.common.exception;

import com.wrp.blog.common.enums.ResultType;
import lombok.Getter;

/**
 * 自定义异常
 * @author wrp
 * @since 2024年08月17日 15:15
 */
@Getter
public class BusinessException extends RuntimeException {

   private final ResultType resultType;

    public BusinessException(ResultType resultType) {
        this.resultType = resultType;
    }

    public static BusinessException of(ResultType resultType) {
        return new BusinessException(resultType);
    }
}
