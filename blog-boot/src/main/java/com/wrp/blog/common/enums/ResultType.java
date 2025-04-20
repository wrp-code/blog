package com.wrp.blog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wrp
 * @since 2024-10-26 18:25
 **/
@Getter
@AllArgsConstructor
public enum ResultType {
    OK(10000, "成功"),
    S_MISS_TOKEN(10001, "未设置令牌"),
    S_INVALID_TOKEN(10002, "无效令牌"),
    S_NOT_FOUND(10003, "资源不存在"),
    S_ERROR_ARGS(10004, "请求参数有问题"),
    S_LACK_SOURCE(10005, "系统资源不足"),
    S_FILE_TYPE_NOT_SUPPORT(10006, "文件类型不支持"),
    U_USERNAME_USED(20001, "用户名已注册"),
    U_PHONE_USED(20002, "手机号已存在用户"),
    U_USERNAME_NOT_FOUND(20003, "用户名错误"),
    U_PASSWORD_ERROR(20004, "密码错误"),
    U_NO_LOGIN(20005, "用户未登录"),
    U_INVALID_OPERATION(20006, "用户非法操作"),
    FAIL(99999, "系统内部异常"),
    ;

    private final int code;
    private final String message;
}
