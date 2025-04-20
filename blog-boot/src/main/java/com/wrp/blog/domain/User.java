package com.wrp.blog.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户
 * @author wrp
 * @since 2024-09-08 18:36
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("public.b_user")
public class User extends BaseEntity {
    private String username;
    private String password;
    private String phone;
    private String email;
    private String description;
    private Long avatar;
}
