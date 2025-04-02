package com.wrp.blog.controller.support;

import lombok.Data;

/**
 * @author wrp
 * @since 2024-09-08 21:53
 **/
@Data
public class UpdateUserParam {
    private String username;
    private String password;
    private String post;
    private String photo;
    private String phone;
    private String email;
    private String qq;
}
