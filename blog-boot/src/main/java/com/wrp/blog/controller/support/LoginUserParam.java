package com.wrp.blog.controller.support;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author wrp
 * @since 2024-09-08 21:51
 **/
@Data
public class LoginUserParam {
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password;
}
