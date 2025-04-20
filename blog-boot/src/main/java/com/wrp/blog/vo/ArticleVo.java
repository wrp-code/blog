package com.wrp.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wrp
 * @since 2024-09-08 21:23
 **/
@Data
public class ArticleVo {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private LocalDateTime createTime;
    private String title;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;
    private String username;
    private Long hits;
}
