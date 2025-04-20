package com.wrp.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wrp
 * @since 2025-04-20 09:05
 **/
@Data
public class ArticleListVo {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private LocalDateTime createTime;
    private String title;
    private String username;
    private Long hits;
}
