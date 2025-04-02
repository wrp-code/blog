package com.wrp.blog.controller.support;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author wrp
 * @since 2024-09-08 21:46
 **/
@Data
public class RegisterUserParam {
    @NotEmpty(message = "用户名不能为空")
    @Length(min = 2, max = 16, message = "用户名字符2~16")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password;
    private String photo;
    @Pattern(regexp = "(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}", message = "手机号格式错误")
    @NotEmpty(message = "电话不能为空")
    private String phone;
    @Pattern(regexp = "[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+", message = "邮箱格式错误")
    private String email;
    @Length(min = 5, max = 11, message = "qq号格式错误")
    private String qq;
    private String description;
}
