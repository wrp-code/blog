package com.wrp.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author wrp
 * @since 2024-09-08 21:50
 **/
@Data
public class UserVo {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String username;
    private String photo;
    private String phone;
    private String email;
    private String qq;
    private String token;
    private String description;
}
