package com.wrp.blog.common.result;

/**
 * 所有的web请求结果
 *
 * @author wrp
 * @see ResultUtils
 * @since 2024年08月09日 19:36
 */
public record Result<T>(int code, String message, T data) {
}
