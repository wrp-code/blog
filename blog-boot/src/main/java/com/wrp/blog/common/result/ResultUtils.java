package com.wrp.blog.common.result;

import com.wrp.blog.common.enums.ResultType;

/**
 * @author wrp
 * @since 2024年08月09日 19:39
 */
public class ResultUtils {

    public static Result<Void> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return success(ResultType.OK.getCode(), null, data);
    }

    public static <T> Result<T> success(int code, String msg, T data) {
        return getResultInstance(code, msg, data);
    }

    public static <T> Result<T> error(ResultType resultType) {
        return getResultInstance(resultType.getCode(), resultType.getMessage(), null);
    }

    public static <T> Result<T> error(String message) {
        return getResultInstance(ResultType.S_ERROR_ARGS.getCode(), message, null);
    }

    private static <T> Result<T> getResultInstance(int code, String msg, T data) {
        return new Result<>(code, msg, data);
    }
}
