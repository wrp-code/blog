package com.wrp.blog.controller.support;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 更新
 * @author wrp
 * @since 2024-09-08 20:24
 **/
@Data
public class UpdateArticleParam {
    @NotNull(message = "未指定文章")
    private Long id;
    private String title;
    /**
     * 主体内容
     */
    private String content;
}
